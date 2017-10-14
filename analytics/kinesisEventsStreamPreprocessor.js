'use strict';

console.log('Loading transformation function');

function processEvent(event) {
    if (event.type === 'TRACK') {
        let track = {
            type: event.type,
            key: event.key,
            timestamp: event.track.timestamp,
            name: event.track.track.name,
            disc_number: event.track.track.discNumber,
            track_number: event.track.track.trackNumber,
            track_popularity: event.track.track.popularity,
            duration: event.track.track.duration,
        };

        if (event.track.track.album) {
            track = Object.assign(track, {
                artist: event.track.track.album.artists[0].name,
                label: event.track.track.album.label,
                album_name: event.track.track.album.name,
                genres: event.track.track.album.genres,
                album_popularity: event.track.track.album.popularity,
                release_date: event.track.track.album.releaseDate,
                release_date_precision: event.track.track.album.releaseDatePrecision,
            });
        }

        if (event.track.track.features) {
            track = Object.assign(track, {
                mode: event.track.track.features.mode,
                tonality: event.track.track.features.key,
                time_signature: event.track.track.features.timeSignature,
                tempo: event.track.track.features.tempo,
                acousticness: event.track.track.features.acousticness,
                danceability: event.track.track.features.danceability,
                energy: event.track.track.features.energy,
                instrumentalness: event.track.track.features.instrumentalness,
                liveness: event.track.track.features.liveness,
                loudness: event.track.track.features.loudness,
                speechiness: event.track.track.features.speechiness,
                valence: event.track.track.features.valence,
            });

        } else {
            return [];  // don't want to count tracks without features in analytics
        }

        return [
            track
        ];

    } else if (event.type === 'PAYMENT') {
        let itemsCount = 0;
        let items;

        if (event.payment.items) {
            items = event.payment.items.map(
                (item) => {
                    let itemEvent = {
                        type: 'ITEM',
                        key: event.key,
                        timestamp: event.payment.timestamp,
                        device: event.payment.device,
                        name: item.name,
                        quantity: item.quantity,
                        category: item.category,
                        variation: item.variation,
                        sku: item.sku,
                        total: item.total,
                        single_quantity: item.singleQuantity,
                        gross_sales: item.grossSales,
                        net_sales: item.netSales,
                    };

                    if (item.modifiers.length > 0) {
                        return Object.assign(itemEvent, {
                            modifier_type: item.modifiers[0].type,
                            modifier_name: item.modifiers[0].name,
                        });

                    } else {
                        return itemEvent;
                    }
                }
            );

            itemsCount = items.length;
        }

        let paymentEvent = [
            {
                type: event.type,
                key: event.key,
                timestamp: event.payment.timestamp,
                device: event.payment.device,
                items_count: itemsCount,
                collected: event.payment.collected,
                refunded: event.payment.refunded,
                discount: event.payment.discount,
                tip: event.payment.tip,
                tax: event.payment.tax,
                fee: event.payment.fee,
                tender_type: event.payment.tenders[0].type,
                tender_card_brand: event.payment.tenders[0].cardBrand,
                tender_card_suffix: event.payment.tenders[0].panSuffix,
                tender_entry_method: event.payment.tenders[0].entryMethod,
            }
        ];

        if (event.payment.items) {
            return paymentEvent.concat(
                items
            );

        } else {
            return paymentEvent;
        }

    } else {
        return [];
    }
}

function processRecord(record) {
    let processedEvents = processEvent(JSON
            .parse(Buffer
                .from(record.data, 'base64')
                .toString('utf8')));

    if (processedEvents.length > 0) {
        let recordPayload = Buffer.from(
            processedEvents.map(
                (processedEvent) => JSON.stringify(processedEvent)
            ).join('')
        ).toString('base64');

        return {
            recordId: record.recordId,
            result: 'Ok',
            data: recordPayload,
        };

    } else {
        return {
            recordId: record.recordId,
            result: 'Dropped',
            data: record.data,
        };
    }
}

exports.handler = (event, context, callback) => {
    const output = event.records.map(
        (record) => processRecord(record)
    );
    console.log(`Processing completed.  Successful records ${output.length}.`);
    callback(null, { records: output });
};

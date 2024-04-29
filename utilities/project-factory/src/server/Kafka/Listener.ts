import { Consumer, KafkaClient, Message } from 'kafka-node';
import config from '../config';
import { logger } from '../utils/logger'; // Importing logger utility for logging
import { producer } from './Producer'; // Importing producer from the Producer module



// Replace with the correct Kafka broker(s) and topic name
const kafkaConfig = {
    kafkaHost: config.KAFKA_BROKER_HOST, // Use the correct broker address and port
    autoCommit: true,
    autoCommitIntervalMs: 5000,
    fromOffset: 'earliest', // Start reading from the beginning of the topic
};

const topicName = config.KAFKA_START_CAMPAIGN_MAPPING_TOPIC;

// Create a Kafka client
const kafkaClient = new KafkaClient(kafkaConfig);

// Create a Kafka consumer
const consumer = new Consumer(kafkaClient, [{ topic: topicName, partition: 0 }], { autoCommit: true });


// Exported listener function
export function listener() {
    // Set up a message event handler
    consumer.on('message', async (message: Message) => {
        try {
            // Parse the message value as an array of objects
            const messageObject: any = JSON.parse(message.value?.toString() || '{}');
            // await processCampaignMapping(messageObject);
            console.log(messageObject, " mmmmmmmmmmmmmmmmmmmmmmmm")

            logger.info(`Received message: ${JSON.stringify(messageObject)}`)
        } catch (error) {
            console.error(`Error processing message: ${error}`);
        }
    });

    // Set up error event handlers
    consumer.on('error', (err) => {
        console.error(`Consumer Error: ${err}`);
    });

    consumer.on('offsetOutOfRange', (err) => {
        console.error(`Offset out of range error: ${err}`);
    });
}


/**
 * Produces modified messages to a specified Kafka topic.
 * @param modifiedMessages An array of modified messages to be produced.
 * @param topic The Kafka topic to which the messages will be produced.
 * @returns A promise that resolves when the messages are successfully produced.
 */
async function produceModifiedMessages(modifiedMessages: any[], topic: any) {
    return new Promise<void>((resolve, reject) => {
        const payloads = [
            {
                topic: topic,
                messages: JSON.stringify(modifiedMessages), // Convert modified messages to JSON string
            },
        ];

        // Send payloads to the Kafka producer
        producer.send(payloads, (err) => {
            if (err) {
                logger.info(`Producer Error: ${JSON.stringify(err)}`); // Log producer error
                reject(err); // Reject promise if there's an error
            } else {
                logger.info('Produced modified messages successfully.'); // Log successful message production
                resolve(); // Resolve promise if messages are successfully produced
            }
        });
    });
}

export { produceModifiedMessages } // Export the produceModifiedMessages function for external use

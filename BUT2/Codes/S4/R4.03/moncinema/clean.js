const { MongoClient } = require('mongodb');

const url = 'mongodb://localhost:27017';
const client = new MongoClient(url);

async function dropDatabase() {
    try {
        await client.connect();
        console.log("Connected successfully to server");

        await client.db("Cinema").dropDatabase();
        console.log("Dropped database : Cinema");

    } catch (err) {
        console.error(`Failed to drop database : ${err}`);
    } finally {
        await client.close();
    }
}

dropDatabase().catch(console.dir);

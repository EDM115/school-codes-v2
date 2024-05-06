import fs from 'node:fs/promises';

function toUpperCase(str: string): string {
  return str.toUpperCase();
}

async function starshipsAPI(page: number): Promise<object> {
  const response = await fs.readFile(`${__dirname}/resources/starships-page${page}.json`, { encoding: 'utf8' });
  return JSON.parse(response);
}

export { toUpperCase, starshipsAPI };
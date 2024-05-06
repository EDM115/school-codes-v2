import { expect, it } from 'vitest';
import { starshipsAPI, toUpperCase } from '.';

it('toUpperCase', () => {
  const result = toUpperCase('foobar');
  expect(result).toMatchSnapshot();
})

it('starship API', async () => {
  const result = await starshipsAPI(1);
  expect(result).toMatchSnapshot();
})
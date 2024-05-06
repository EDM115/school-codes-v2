import { hello } from './index';

describe('Hello tdd test suite', () => {
  test('should return "Hello world !" sentence', () => {
    expect(hello()).toEqual("Hello world !");
  });
})
- **Scroll element into view in a single function** :
  `element.scrollIntoView({ behavior: "smooth" });`

- **"Go back" button"** :
  `history.back()`

- **Run event listener only once** :
  `element.addEventListener('click', () => console.log("run once"), { once: true });`

- **Wrap console.log() arguments with curly brackets to see variable names** :
  `console.log({variable});`

- **Get min/max value from an array** :

```javascript
const numbers = [6, 8, 1, 3, 9];
console.log(Math.max(...numbers)); // 9
console.log(Math.min(...numbers)); // 1
```

- **Sum elements in an array** :

```javascript
const numbers = [10, 20, 30, 40];
const sum = numbers.reduce((x, y) => x + y, 0);
console.log(sum); // 100
```

- **Find first element in array to pass a condition** :

```javascript
const numbers = [7, 14, 8, 128, 56];
const found = numbers.find((element) => element > 10);
console.log(found); // 14
```

Or to get its index

```javascript
const indexFound = numbers.findIndex((element) => element > 15);
console.log(indexFound); // 3
```

- **SHA256 Hash implementation in javascript** :
  [JS File from TheAlgorithms' javascript Github repo](https://cdn.jsdelivr.net/gh/TheAlgorithms/Javascript/Hashes/SHA256.js)

- **Working with videos made easy** :
  [Video.js](https://videojs.com/)

## Javascript style guide

On Airbnb's [Github](https://github.com/airbnb/javascript)

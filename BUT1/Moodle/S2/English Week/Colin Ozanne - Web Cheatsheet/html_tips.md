- **HTML boilerplate to start a page** :
  [HTML Boilerplate by matuzo](https://www.matuzo.at/blog/html-boilerplate/)

- **Change image depending on screen size** :

```html
<picture>
  <source media="(min-width:650px)" src="/path" />
  <img src="/path" />
</picture>
```

- **Icons using HTML** :

```html
<h1>&check;</h1>
<h1>&cross;</h1>
<h1>&malt;</h1>
<h1>&sext;</h1>
```

- **Make specific areas on img clickable** :
  [W3Schools Tryit Editor](https://www.w3schools.com/tags/tryit.asp?filename=tryhtml_areamap)

- **Custom keyboard shortcuts** :
  `<a href="#" accesskey="a">Keyboard activated</a>`

- **Block translation on specific element** :
  `<p translate="no">Company name</p>`

- **Sandbox iframe** :
  `<iframe src="#" sandbox>`

- **Regex to check pattern** :
  `<input type="password" pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}">`

- **Restrict file type on input:file** :
  `<input type="file" accept="image/*">`

- **Make element draggable** :
  `<p draggable="true">Drag Me</p>`

- **Native HTML Search in input** :

```html
<input list="items" />

<datalist id="items">
  <option value="Marko Denic"></option>
  <option value="FreeCodeCamp"></option>
  <option value="FreeCodeTools"></option>
  <option value="Web Development"></option>
  <option value="Web Developer"></option>
</datalist>
```

- **Defer the loading of the image until the user scrolls to them** :
  `<img src='image.jpg' loading='lazy' alt='Alternative Text'>`

- **Email, call, and SMS links** :

```html
<a href="mailto:{email}?subject={subject}&body={content}"> Send us an email </a>

<a href="tel:{phone}"> Call us </a>

<a href="sms:{phone}?body={content}"> Send us a message </a>
```

- **Change the starting point for anordered list** :

```html
<ol start="11">
  <li>Eleventh item</li>
  <li>Twelfth item</li>
</ol>
```

- **Display quatities as progress bars** :

```html
<label for="value1">Low</label>
<meter
  id="value1"
  min="0"
  max="100"
  low="30"
  high="75"
  optimum="80"
  value="25"
></meter>

<label for="value2">Medium</label>
<meter
  id="value2"
  min="0"
  max="100"
  low="30"
  high="75"
  optimum="80"
  value="50"
></meter>

<label for="value3">High</label>
<meter
  id="value3"
  min="0"
  max="100"
  low="30"
  high="75"
  optimum="80"
  value="80"
></meter>
```

- **Open all links in new tab** :
  Add the `<base>` element to your `<head>`

```html
<head>
  <base target="_blank" />
</head>
```

- **Download link instead of diaplaying it** :

```html
<a href="path/to/file" download> Download </a>
```

- **Use a fallback image if .webp isn't supported by the browser** :

```html
<picture>
  <!-- load .webp image if supported -->
  <source srcset="logo.webp" type="image/webp" />
  <!-- Fallback if `.webp` images or <picture> tag not supported by the browser -->
  <img src="logo.png" alt="logo" />
</picture>
```

- **Open device camera to capture image** :

```html
<input type="file" capture="{user|environnement}" accept="image/*>
```

- **Favicon Boilerplate (short version)** :

```html
<link rel="icon" href="/favicon.ico" /><!-- 32×32 -->
<link rel="icon" href="/icon.svg" type="image/svg+xml" />
<link rel="apple-touch-icon" href="/apple-touch-icon.png" /><!-- 180×180 -->
<link rel="manifest" href="/manifest.webmanifest" />
```

- **Prevent background image clipping** :
  `background-repeat: space | round`

- **CSS patterns** :
  [csslayout.io](https://csslayout.io/patterns)

- **CSS Scroll shadow** :
  [css-scroll-shadows.vercel.app](https://css-scroll-shadows.vercel.app)

- **Free design tools** :
  [Undesign](https://undesign.learn.uno/)

- **Smooth shadow generator** :
  [shadows.brumm.af](https://shadows.brumm.af/)

- **Stripes generator** :
  [CSS Stripes Generator](https://stripesgenerator.com/)

- **Pure HTML/CSS Accordeon slider generator** :
  [CSS Accordion Slider Generator](https://accordionslider.com/)

- **Layout generator** :
  [Layoutit!](https://grid.layoutit.com/)

- **Animation maker** :
  [Keyframes](https://keyframes.app/)

- **Create Neumophism buttons** :
  [Neumorphism.io](https://neumorphism.io/#e0e0e0)

- **Build a fancy hover animation** :
  [Picalil.li](https://piccalil.li/tutorial/build-a-fancy-hover-animation)

- **:checked, sibling combinators +/~, and the native behavior of labels for animation** :
  [Checkbox Hack Codepen](https://codepen.io/jh3y/pen/eYgeVYJ)

- **Full-screen scroll-snap starter** :
  [Full Screen Vertical Scroll Snap](https://codepen.io/argyleink/pen/qBRpdEr)

- **Best CSS pseudo classes** :
  [Thread by @Prathkum](https://threadreaderapp.com/thread/1381539343165186050.html)

- **Text outline** :
  `-webkit-text-stroke: 2px red;`

- **Pure CSS moon** :

```css
.moon {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  box-shadow: 22px 22px 0 0 black;
}
```

- **Customise list marker** :

```css
::marker {
  color: #f2d5d6;
  font-family: "Poppins", sans-serif;
  text-shaddow: 2px 2px black;
}
```

- **Find great looking gradients for backgrounds** :
  [Gradienta](https://gradienta.io/)

- **Pure CSS text portrait** :

```css
p {
  background: url("#");
  -webkit-background-clip: text;
}
```

- **Create a typewriter effect on text** :

```css
@import url("https://fonts.googleapis.com/css2?family=Inconsolata:wght@500&display=swap");

.text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-right: 4px solid black;
  font-size: 180%;
  white-space: nowrap;
  overflow: hidden;
}

.animation {
  animation: typewriter 4s steps(15) 1s 1 normal both, blinkCursor 500ms steps(
        15
      ) infinite normal;
}

@keyframes typewriter {
  from {
    width: 0;
  }
  to {
    width: 214px;
  }
}

@keyframes blinkCursor {
  from {
    border-right-color: black;
  }
  to {
    border-right-color: transparent;
  }
}
```

- **Truncate text using only CSS** :

```css
h1 {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
```

- **Reset default CSS values** :
  [A Modern CSS Reset - Piccalilli](https://piccalil.li/blog/a-modern-css-reset/)

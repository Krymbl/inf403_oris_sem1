//6. Написать JS, который проверяет
// у всех изображений alt и записывает в
// него значение src, если alt пустой.

const pictures = document.querySelectorAll("img");

for (const picture of pictures) {
    if (!picture.alt || picture.alt === "") {
        picture.alt = picture.src;
    }
}
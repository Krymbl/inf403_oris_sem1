
//скрипт: пройтись по всем картинкам, если alt пустой, то записать в него соот. картинку (значение src)

const pictures = document.getElementsByTagName("img");

for (const picture of pictures) {
    if (picture.alt === "") {
        picture.alt = picture.src;
    }
}
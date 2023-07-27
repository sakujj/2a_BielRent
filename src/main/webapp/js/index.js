document.addEventListener("readystatechange", (event) => {
    if (event.target.readyState === "complete") {
        disableLinks();
    }
})

function disableLinks() {
    const disabledElems = document.getElementsByClassName("disabled");

    for (let i = 0; i < disabledElems.length; i++) {
        let disabledElem = disabledElems[i];
        if (!disabledElem.classList.contains("page-link"))
            continue;

        disabledElem.href = "javascript:void(0)";
    }
}
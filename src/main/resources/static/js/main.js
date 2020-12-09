function clearFilter() {
    document.getElementById('keyword').value = "";
    filterBoards();
}

function filterBoards() {
    let filterCriteria =document.getElementById('keyword').value.toString().toLocaleLowerCase();
    let filterableElements;
    let count = 0;
    filterableElements = document.getElementsByClassName("filterable");

    for (let index = 0; index < filterableElements.length; index++) {
        let elementAtIndex = filterableElements[index];
        let selectedDescendant = elementAtIndex.querySelector('.boardName');

        if (selectedDescendant.innerHTML.toLocaleLowerCase().includes(filterCriteria)) {
            showElement(elementAtIndex);
            count++;
        } else {
            hideElement(elementAtIndex);
        }
    }
    let resultWrapper =  document.getElementById('resultWrapper')
    if (count>10){
        addCssClass(resultWrapper, "vertical-overflow-container");
    } else {
        removeCssClass(resultWrapper, "vertical-overflow-container")
    }
}

function showElement(element) {
    removeCssClass(element, "hide");
    addCssClass(element, "show");
}

function hideElement(element) {
    removeCssClass(element, "show");
    addCssClass(element, "hide");
}

function addCssClass(element, classes) {
    var i, arr1, arr2;
    arr1 = element.className.split(" ");
    arr2 = classes.split(" ");
    for (i = 0; i < arr2.length; i++) {
        if (arr1.indexOf(arr2[i]) == -1) {
            element.className += " " + arr2[i];
        }
    }
}

function removeCssClass(element, classes) {
    var i, arr1, arr2;
    arr1 = element.className.split(" ");
    arr2 = classes.split(" ");
    for (i = 0; i < arr2.length; i++) {
        while (arr1.indexOf(arr2[i]) > -1) {
            arr1.splice(arr1.indexOf(arr2[i]), 1);
        }
    }
    element.className = arr1.join(" ");
}

$('#submit-form').on('click', function () {
    var form = $('#getBoardForm');
    $.ajax({
        url: form.attr('action'),
        data: form.serialize(),
        type: form.attr("method"),
        success: function (result) {
            // Do something with the response.
            // Might want to check for errors here.
        }, error: function (error) {
            // Here you can handle exceptions thrown by the server or your controller.
        }
    })
})

document.addEventListener('DOMContentLoaded', (event) => {
    filterBoards();
});
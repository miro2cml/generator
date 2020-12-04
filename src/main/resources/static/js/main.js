function clearFilter() {
    document.getElementById('keyword').value = "";
    filterBoards();
}

function filterBoards() {
    let filterCriteria = document.getElementById('keyword').value
    var filterableElements, index;
    filterableElements = document.getElementsByClassName("filterable");

    for (index = 0; index < filterableElements.length; index++) {
        let elementAtIndex = filterableElements[index];
        let rightDescendant = elementAtIndex.querySelector('.boardName');

        if (rightDescendant.innerHTML.includes(filterCriteria)) {
            showElement(elementAtIndex)
        } else {
            hideElement(elementAtIndex)
        }
    }
}

function showElement(element) {
    removeCssClass(element, "show");
    removeCssClass(element, "hide");
    addCssClass(element, "show");
}

function hideElement(element) {
    removeCssClass(element, "show");
    removeCssClass(element, "hide");
    addCssClass(element, "hide");
}

function addCssClass(element, name) {
    var i, arr1, arr2;
    arr1 = element.className.split(" ");
    arr2 = name.split(" ");
    for (i = 0; i < arr2.length; i++) {
        if (arr1.indexOf(arr2[i]) == -1) {
            element.className += " " + arr2[i];
        }
    }
}

function removeCssClass(element, name) {
    var i, arr1, arr2;
    arr1 = element.className.split(" ");
    arr2 = name.split(" ");
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
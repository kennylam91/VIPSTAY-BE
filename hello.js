$(document).ready(function () {
    $.ajax({
        url: "http://localhost:8080/api/houses",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU2NjYzMTk2NiwiaWF0IjoxNTY2NjEzOTY2fQ.Y6q6F7DAoLU3ucvIBvQwjtzzOax1n4WbV9hoVrsGuZ6AarSp-WTDmgxbEsuhgqpRyY2RnN31ynolnT0C6KffSw")
        }
    }).then(function (data, status, jqxhr) {
        console.log(data, status, jqxhr);
    })
})
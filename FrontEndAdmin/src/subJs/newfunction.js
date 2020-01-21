// Add the following code if you want the name of the file appear on select

$(".custom-file-input").on("change", function () {
    alert("weslt");
    var fileName = $(this).val().split("\\").pop();
    console.log(fileName);
    $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});

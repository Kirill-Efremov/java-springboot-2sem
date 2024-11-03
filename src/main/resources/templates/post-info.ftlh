<!DOCTYPE html>
<html>
<head>
    <title>Catalog</title>
    <link href="../static/css/post-info.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="post-photo">
        <img class="photo" alt="IMAGE" id="postPhoto" src="/static/photo/no-photo.jpg"   />
    </div>

    <div class="post-info">
        <b>Photo id:</b><span id="photoId"></span><br>
        <b>Название объявления:</b><span id="postTitle"></span><br>
        <b>Описание объявления:</b><span id="postContent"></span><br>
        <b>Цена:</b><span id="postPrice"></span><br>
        <b>Город:</b><span id="postCity"></span><br>
        <b>Автор:</b><a href="#" id="authorLink" class="author-link"></a><br>
    </div>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        var postId = window.location.pathname.split('/').pop();
        fetch("/api/post/" + postId)
            .then(response => response.json())
            .then(post => {
                document.getElementById("postTitle").innerText = post.title;
                document.getElementById("postContent").innerText = post.content;
                document.getElementById("postPrice").innerText = post.price;
                document.getElementById("postCity").innerText = post.city;
                document.getElementById("photoId").innerText = post.userId;
                if (post.photo) {
                    document.getElementById("postPhoto").src = "/files/" + post.photo.id;
                } else {
                    document.getElementById("postPhoto").src = "/static/photo/no-photo.jpg";
                }
                if (post.userId) {
                    fetch("/api/post/" +  post.id  + "/user/" )
                        .then(response => response.json())
                        .then(user => {
                            document.getElementById("authorLink").innerText = user.name;
                            document.getElementById("authorLink").href = "/user/" + user.id;
                        })
                        .catch(error => {
                            console.error('Error fetching user:', error);
                            document.getElementById("authorLink").innerText = "Имя неизвестно UserId";
                        });
                } else {
                    document.getElementById("authorLink").innerText = "Имя неизвестно";
                }
            })
            .catch(error => console.error('Error fetching post:', error));
    });
</script>
</body>
</html>

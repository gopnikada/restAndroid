package com.example.simplerest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.text_view_result);
        Gson gson = new GsonBuilder().serializeNulls().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        getPosts();
//        getComments();
//        createPost();
//        updatePost();
       // deletePost();

    }


    private void getPosts(){
        Map<String, String> parameters  = new HashMap<>();
        parameters.put("userId", "1");
        parameters.put("_sort", "id");
        parameters.put("_order", "desc");

        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(parameters);

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText(response.code());
                    return;
                }
                List<Post> posts = response.body();//getting the actual posts

                for(Post post : posts){
                    String content = "";
                    content += "Post ID: " + post.getId() + "\n";
                    content += "user ID: " + post.getUserId() + "\n";
                    content += "Post Title: " + post.getTitle() + "\n";
                    content += "Post Text: " + post.getText() + "\n\n";
                    textViewResult.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage());

            }
        });
    }

    private void getComments(){
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments("posts/3/comments");

        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText(response.code());
                    return;
                }
                List<Comment> comments = response.body();

                for(Comment comment : comments){
                    String content = "";
                    content += "Post ID: " + comment.getId() + "\n";
                    content += "user ID: " + comment.getPostId() + "\n";
                    content += "Comment Title: " + comment.getName() + "\n";
                    content += "User Email: " + comment.getEmail() + "\n";
                    content += "Comment Text: " + comment.getText() + "\n\n";
                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost() {
        //Post post = new Post(24, "Sample title", "Sample text");
        Map <String, String> fields = new HashMap<>();
        fields.put("userId", "26");
        fields.put("text", "ttttt");
        fields.put("title", "titleee");


        Call<Post> call = jsonPlaceHolderApi.createPost(fields);
        call.enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if(!response.isSuccessful()){
                        textViewResult.setText(response.code());
                        return;
                    }
                    Post postResponse = response.body();

                    String content = "";
                    content += "Code: " + response.code() + "\n";
                    content += "Post ID: " + postResponse.getId() + "\n";
                    content += "user ID: " + postResponse.getUserId() + "\n";
                    content += "Post Title: " + postResponse.getTitle() + "\n";
                    content += "Post Text: " + postResponse.getText() + "\n\n";
                    textViewResult.append(content);

                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    textViewResult.setText(t.getMessage());
                }
        });
    }

    private void updatePost() {
        Post post = new Post(14, "Some title", "Some text");
        Call<Post> call = jsonPlaceHolderApi.putPost(5, post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText(response.code());
                    return;
                }
                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "Post ID: " + postResponse.getId() + "\n";
                content += "user ID: " + postResponse.getUserId() + "\n";
                content += "Post Title: " + postResponse.getTitle() + "\n";
                content += "Post Text: " + postResponse.getText() + "\n\n";
                textViewResult.append(content);

            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }


    private void deletePost() {
        Call<Void> call = jsonPlaceHolderApi.deletePost(2);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText(response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}
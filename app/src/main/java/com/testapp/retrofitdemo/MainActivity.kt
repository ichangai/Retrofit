package com.testapp.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var retService : AlbumService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        retService = RetrofitInstance
            .getRetrofitInstance()
            .create(AlbumService::class.java)
        getRequestWithQueryParameters()
//        getRequestWithPathParameters()
    }

    private fun getRequestWithQueryParameters(){
        val responseLiveData: LiveData<Response<Album>> = liveData{
            val response: Response<Album> = retService.getSortedAlbums(3)
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            val albumList: MutableListIterator<AlbumItem>? = it.body()?.listIterator()
            if(albumList!=null){
                while (albumList.hasNext()){
                    val albumItem: AlbumItem = albumList.next()
//                    Log.i("MYTAG", albumItem.title)
                    var result: String = " " + "Album Title: ${albumItem.title}"+"\n"+
                            " " + "Album id: ${albumItem.id}"+"\n"+
                            " " + "User id: ${albumItem.userId}"+"\n\n\n"
                    val displayText = findViewById<TextView>(R.id.text_view)
                    displayText.append(result)

                }
            }
        })
    }

    private fun getRequestWithPathParameters(){
        //path parameter example
        val pathResponse: LiveData<Response<AlbumItem>> = liveData {
            val response: Response<AlbumItem> =  retService.getAlbum(3)
            emit(response)
        }

        pathResponse.observe(this, Observer{
            val title:String? = it.body()?.title
            Toast.makeText(application, title, Toast.LENGTH_LONG).show()
        })

    }






}


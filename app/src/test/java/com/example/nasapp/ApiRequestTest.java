package com.example.nasapp;


import com.example.nasapp.Model.Apod;
import com.example.nasapp.Model.Epic;
import com.example.nasapp.Retrofit.ApiInterface;
import com.example.nasapp.Retrofit.ApiUtils;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static io.reactivex.Observable.just;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ApiRequestTest {

    /*Create a mock of the apodInterface to test calling apod api and getting an apod.*/
    @Mock
    ApiInterface apodAPIInterface= ApiUtils.getApodApiInterface();

    /*Create a mock of the epicInterface to test calling epic api and getting an epic.*/
    @Mock
    ApiInterface epicAPIInterface= ApiUtils.getEpicApiInterface();


    @Test
    public void apodInterfaceTest(){
        /*Initialize the mock fields.*/
        MockitoAnnotations.initMocks(this);

        String date = "2018-07-26";
        String title = "Picture of the day";
        String imageUrl = "https://apod.nasa.gov/apod/image/1807/QuasarJetDrawing_DESY_3508.jpg";

        /*Set the response mock object for the apod api call.*/
        Apod apod = new Apod();
        apod.setDate(date);
        apod.setTitle(title);
        apod.setHdurl(imageUrl);

        /*Test condition.*/
        when(apodAPIInterface.getApod()).thenReturn(just(apod));

        /*Validate the test result.*/
        assertEquals(apod.getDate(),date);
        assertEquals(apod.getTitle(),title);
        assertEquals(apod.getHdurl(),imageUrl);
    }

    @Test
    public void epicInterfaceTest(){
        /*Initialize the mock fields.*/
        MockitoAnnotations.initMocks(this);

        String epic1Date = "2018-07-14";
        String epic1ImageUrl = "https://epic.gsfc.nasa.gov/archive/natural/2018/07/14/png/epic_1b_20180714005516.png";

        String epic2Date = "2018-07-14";
        String epic2ImageUrl = "https://epic.gsfc.nasa.gov/archive/natural/2018/07/14/png/epic_1b_20180714020043.png";

        /*Set the response mock object for the epic api call.*/
        ArrayList<Epic> epicList = new ArrayList<>();
        Epic epic1 = new Epic();
        epic1.setDate(epic1Date);
        epic1.setImage(epic1ImageUrl);
        Epic epic2 = new Epic();
        epic2.setDate(epic2Date);
        epic2.setImage(epic2ImageUrl);
        epicList.add(epic1);
        epicList.add(epic2);

        /*Test condition.*/
        when(epicAPIInterface.getEpicImageList()).thenReturn(just(epicList));

        /*Validate the test result.*/
        assertEquals(epicList.get(0).getImage(),epic1ImageUrl);
        assertEquals(epicList.get(1).getImage(),epic2ImageUrl);
    }

}

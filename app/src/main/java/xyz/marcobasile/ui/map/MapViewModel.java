package xyz.marcobasile.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is going to be the posts location map");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
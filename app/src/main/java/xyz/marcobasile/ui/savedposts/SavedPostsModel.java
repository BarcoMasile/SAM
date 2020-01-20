package xyz.marcobasile.ui.savedposts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedPostsModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SavedPostsModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
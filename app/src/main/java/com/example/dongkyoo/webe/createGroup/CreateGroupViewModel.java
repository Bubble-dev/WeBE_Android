package com.example.dongkyoo.webe.createGroup;


import android.os.AsyncTask;

import com.example.dongkyoo.webe.Network;
import com.example.dongkyoo.webe.vos.Group;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.lifecycle.ViewModel;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class CreateGroupViewModel extends ViewModel {

    public void saveNewGroup(Network.OnNetworkProcessListener listener) {
        new SaveNewGroupTask(listener).execute();
    }

    private class SaveNewGroupTask extends AsyncTask<Void, Void, Group> {

        private Network.OnNetworkProcessListener listener;

        public SaveNewGroupTask(Network.OnNetworkProcessListener listener) {
            this.listener = listener;
        }

        @Override
        protected Group doInBackground(Void... voids) {
            try {
                OkHttpClient client = new OkHttpClient();

                JSONObject json = new JSONObject();
                json.put("name", "name");
                String jsonData = json.toString();
                RequestBody requestBody = RequestBody.create(MediaType.parse(Network.APPLICATION_JSON), jsonData);

                Request request = new Request.Builder()
                        .url(Network.SERVER_URL + "/groups")
                        .post(requestBody)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    ResponseBody body = response.body();
                    Gson gson = new Gson();
                    Group group = gson.fromJson(body.string(), Group.class);
                    return group;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Group group) {
            if (group == null)
                listener.onFailure();
            else
                listener.onSuccess(Network.makeObjectList(group));
        }
    }
}

package org.smartregister.anc.presenter;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.smartregister.anc.R;
import org.smartregister.anc.contract.ContactContract;
import org.smartregister.anc.domain.Contact;
import org.smartregister.anc.interactor.ContactInteractor;
import org.smartregister.anc.model.ContactModel;

import java.lang.ref.WeakReference;
import java.util.Map;

public class ContactPresenter implements ContactContract.Presenter, ContactContract.InteractorCallBack {

    public static final String TAG = ContactPresenter.class.getName();

    private WeakReference<ContactContract.View> viewReference;
    private ContactContract.Interactor interactor;
    private ContactContract.Model model;

    private String baseEntityId;

    private Map<String, String> details;

    public ContactPresenter(ContactContract.View contactView) {
        viewReference = new WeakReference<>(contactView);
        interactor = new ContactInteractor();
        model = new ContactModel();
    }

    @Override
    public void setBaseEntityId(String baseEntityId) {
        this.baseEntityId = baseEntityId;

        fetchPatient(baseEntityId);
    }

    @Override
    public boolean baseEntityIdExists() {
        return StringUtils.isNotBlank(baseEntityId);
    }

    @Override
    public void fetchPatient(String baseEntityId) {
        interactor.fetchWomanDetails(baseEntityId, this);
    }

    @Override
    public void onWomanDetailsFetched(Map<String, String> womanDetails) {
        if (womanDetails == null || womanDetails.isEmpty()) {
            return;
        }

        this.details = womanDetails;
        String patientName = model.extractPatientName(womanDetails);
        getView().displayPatientName(patientName);

    }

    @Override
    public String getPatientName() {
        if (details == null || details.isEmpty()) {
            return "";
        }
        return model.extractPatientName(details);
    }

    @Override
    public void startForm(Object tag) {

        try {
            if (tag == null || !(tag instanceof Contact)) {
                return;
            }

            Contact contact = (Contact) tag;

            JSONObject form = model.getFormAsJson(contact.getFormName(), baseEntityId, null);
            getView().startFormActivity(form);

        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            getView().displayToast(R.string.error_unable_to_start_form);
        }
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        viewReference = null;//set to null on destroy

        // Activity destroyed set interactor to null
        if (!isChangingConfiguration) {
            interactor = null;
        }
    }

    private ContactContract.View getView() {
        if (viewReference != null)
            return viewReference.get();
        else
            return null;
    }

    // Test methods
    public WeakReference<ContactContract.View> getViewReference() {
        return viewReference;
    }

    public ContactContract.Interactor getInteractor() {
        return interactor;
    }

    public void setInteractor(ContactContract.Interactor interactor) {
        this.interactor = interactor;
    }

    public void setModel(ContactContract.Model model) {
        this.model = model;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }

}

package org.smartregister.anc.contract;

import android.content.Context;

import org.smartregister.anc.domain.QuickCheck;
import org.smartregister.anc.domain.QuickCheckConfiguration;
import org.smartregister.configurableviews.model.Field;


public interface QuickCheckContract {
    interface Presenter {

        void setReason(Field reason);

        QuickCheckConfiguration getConfig();

        boolean containsComplaintOrDangerSign(Field field, boolean isDangerSign);

        void modifyComplaintsOrDangerList(Field field, boolean isChecked, boolean isDangerSign);

        void proceedToNormalContact(String specify);

        void referAndCloseContact(String specify, Boolean refer);

        void setBaseEntityId(String baseEntityId);

    }

    interface View {

        Context getContext();

        String getString(int resId);

        void displayComplaintLayout();

        void hideComplaintLayout();

        void notifyComplaintAdapter();

        void displayDangerSignLayout();

        void notifyDangerSignAdapter();

        void showSpecifyEditText();

        void hideSpecifyEditText();

        void displayNavigationLayout();

        void displayReferButton();

        void hideReferButton();

        void displayToast(int resourceId);

        void dismiss();

        void proceedToContact(String baseEntityId);

    }

    interface Interactor {

        void saveQuickCheckEvent(QuickCheck quickCheck, String baseEntityId, InteractorCallback callback);

    }

    interface InteractorCallback {

        void quickCheckSaved(boolean proceed, boolean saved);
    }

}

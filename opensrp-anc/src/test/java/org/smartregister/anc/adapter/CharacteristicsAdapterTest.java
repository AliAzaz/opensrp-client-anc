package org.smartregister.anc.adapter;

import android.widget.LinearLayout;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.reflect.Whitebox;
import org.robolectric.RuntimeEnvironment;
import org.smartregister.anc.activity.BaseUnitTest;
import org.smartregister.domain.Characteristic;

import java.util.List;

/**
 * Created by ndegwamartin on 20/08/2018.
 */
public class CharacteristicsAdapterTest extends BaseUnitTest {

    private CharacteristicsAdapter adapter;

    @Mock
    private List<Characteristic> data;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        adapter = new CharacteristicsAdapter(RuntimeEnvironment.application, data);

    }

    @Test
    public void testPopulationCharacteristicsAdapterInstantiatesCorrectly() {
        Assert.assertNotNull(adapter);
    }

    @Test
    public void testOnCreateViewHolderReturnsValidViewHolderInstance() {

        LinearLayout viewGroup = new LinearLayout(RuntimeEnvironment.application);
        viewGroup.setLayoutParams(new LinearLayout.LayoutParams(100, 200));
        CharacteristicsAdapter.ViewHolder viewHolder = adapter.onCreateViewHolder(viewGroup, 0);
        Assert.assertNotNull(viewHolder);
    }

    @Test
    public void testGetItemCountInvokesGetSizeMethodOfDataList() {

        List<Characteristic> dataSpy = Whitebox.getInternalState(adapter, "mData");
        Assert.assertNotNull(dataSpy);
        adapter.getItemCount();
        Mockito.verify(dataSpy).size();

    }
}

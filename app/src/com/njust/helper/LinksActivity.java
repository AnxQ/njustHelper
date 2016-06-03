package com.njust.helper;

import android.support.annotation.NonNull;

import com.njust.helper.activity.MyListActivity;
import com.njust.helper.databinding.ItemLinkBinding;
import com.njust.helper.model.Link;
import com.njust.helper.tools.AppHttpHelper;
import com.njust.helper.tools.DataBindingHolder;

public class LinksActivity extends MyListActivity<Link, ItemLinkBinding> {
    @NonNull
    @Override
    protected ListRecycleAdapter<Link, ItemLinkBinding> onCreateAdapter() {
        return new LinkAdapter();
    }

    @Override
    protected String buildCacheName() {
        return "links";
    }

    @Override
    protected String getResponse() throws Exception {
        return new AppHttpHelper().getPostResult("links.php", null);
    }

    @Override
    protected Class<Link> getItemClass() {
        return Link.class;
    }

    private static class LinkAdapter extends ListRecycleAdapter<Link, ItemLinkBinding> {
        @Override
        protected int getLayoutRes() {
            return R.layout.item_link;
        }

        @Override
        public void onBindViewHolder(DataBindingHolder<ItemLinkBinding> holder, int position) {
            holder.getDataBinding().setLink(getItem(position));
        }
    }
}
// Generated code from Butter Knife. Do not modify!
package com.robert.rssparsersample;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RssItemsAdapter$ViewHolder_ViewBinding implements Unbinder {
  private RssItemsAdapter.ViewHolder target;

  @UiThread
  public RssItemsAdapter$ViewHolder_ViewBinding(RssItemsAdapter.ViewHolder target, View source) {
    this.target = target;

    target.textTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'textTitle'", TextView.class);
    target.textPubDate = Utils.findRequiredViewAsType(source, R.id.tvPubDate, "field 'textPubDate'", TextView.class);
    target.imageThumb = Utils.findRequiredViewAsType(source, R.id.ivThumb, "field 'imageThumb'", ImageView.class);
    target.llTextContainer = Utils.findRequiredViewAsType(source, R.id.llTextContainer, "field 'llTextContainer'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    RssItemsAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textTitle = null;
    target.textPubDate = null;
    target.imageThumb = null;
    target.llTextContainer = null;
  }
}

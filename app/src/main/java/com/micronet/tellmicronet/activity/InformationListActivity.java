package com.micronet.tellmicronet.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.core.DbxException;
import com.micronet.tellmicronet.InformationType;
import com.micronet.tellmicronet.R;
import com.micronet.tellmicronet.dummy.DummyContent;
import com.micronet.tellmicronet.fragments.BaseInformationFragment;
import com.micronet.tellmicronet.information.large.LargeInformation;
import com.micronet.tellmicronet.util.Devices;
import com.micronet.tellmicronet.util.InformationGatherer;

import java.io.IOException;
import java.util.List;

/**
 * An activity representing a list of Information. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link InformationDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class InformationListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ImageButton saveButton;
    private ProgressBar spinner;
    private static List<InformationType> mValues;
    private final Context thisContext = this;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        saveButton = new ImageButton(this);
        spinner = new ProgressBar(this);
        spinner.setVisibility(View.GONE);
        saveButton.setBackgroundResource(R.drawable.save_icon);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            InformationGatherer.generateZipFromInformation(mValues, Devices.thisDevice());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(thisContext, "File generated", Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(thisContext, e.getMessage(), Toast.LENGTH_LONG);
                                    toast.getView().setBackgroundColor(Color.RED);
                                    toast.show();
                                    e.printStackTrace();
                                }
                            });
                        } catch (DbxException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(thisContext, "Could not upload the zipped folder.", Toast.LENGTH_LONG);
                                    toast.getView().setBackgroundColor(Color.RED);
                                    toast.show();
                                }
                            });
                        } finally {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    spinner.setVisibility(View.GONE);
                                }
                            });
                        }
                    }
                });
                t.start();
            }
        });
        toolbar.addView(saveButton);
        toolbar.addView(spinner);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.information_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.information_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {


        private final InformationListActivity mParentActivity;

        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InformationType item = (InformationType) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    BaseInformationFragment fragment = ((LargeInformation)item.getCommand(Devices.thisDevice())).generateFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.information_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, InformationDetailActivity.class);

                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(InformationListActivity parent,
                                      List<DummyContent.DummyItem> items,
                                      boolean twoPane) {
            mValues = InformationGatherer.largeInformationList(parent);
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.information_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mContentView.setText(mValues.get(position).getInformationName());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id_text);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}

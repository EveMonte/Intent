package by.bstu.fit.savelev.busyday;

import android.content.Context;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.bstu.fit.savelev.busyday.models.Item;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class ActivitiesAdapter extends
        RecyclerView.Adapter<ActivitiesAdapter.ViewHolder> {
    private int position;
    boolean isSwitchView = true;
    public boolean toggleItemViewType () {
        isSwitchView = !isSwitchView;
        return isSwitchView;
    }
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView aname;
        public TextView category;
        public ImageView image;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setPosition(getAdapterPosition());
                    return false;
                }
            });

            aname = (TextView) itemView.findViewById(R.id.rec_name);
            category = (TextView) itemView.findViewById(R.id.rec_category);
            image = (ImageView) itemView.findViewById(R.id.rec_image);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            //menuInfo is null
//            contextMenu.add(Menu.NONE, R.id.view,
//                    Menu.NONE, R.string.view);
//            contextMenu.add(Menu.NONE, R.id.edit,
//                    Menu.NONE, R.string.edit);
//            contextMenu.add(Menu.NONE, R.id.delete,
//                    Menu.NONE, R.string.delete);
        }
    }

    private List<Item> mActivities;

    // Pass in the contact array into the constructor
    public ActivitiesAdapter(List<Item> activities) {
        mActivities = activities;
    }

    @Override
    public ActivitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recycle_view_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ActivitiesAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Item activity = mActivities.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.aname;
        textView.setText(activity.getActivityName());
        TextView category = holder.category;
        category.setText(activity.getActivityCategory().getValue());
        ImageView image = holder.image;
        try {
            if (activity.getPhoto() != null)
                image.setImageURI(Uri.parse(activity.getPhoto()));
        }
        catch (SecurityException ex){

        }
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mActivities.size();
    }

}

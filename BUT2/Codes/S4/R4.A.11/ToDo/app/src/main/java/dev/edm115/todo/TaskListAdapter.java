package dev.edm115.todo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;

import java.util.ArrayList;

public class TaskListAdapter extends ArrayAdapter<Task> {

    public TaskListAdapter(@NonNull Context context, ArrayList<Task> tasks) {
        super(context, 0, tasks);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Task task = getItem(position);
        if (task == null) return super.getView(position, convertView, parent);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        TextView tvTaskTitle = convertView.findViewById(R.id.tvTaskTitle);
        TextView tvTaskDescription = convertView.findViewById(R.id.tvTaskDescription);
        TextView tvTaskDate = convertView.findViewById(R.id.tvTaskDate);
        TextView tvTaskDuration = convertView.findViewById(R.id.tvTaskDuration);
        TextView tvTaskContext = convertView.findViewById(R.id.tvTaskContext);
        TextView tvTaskStatus = convertView.findViewById(R.id.tvTaskStatus);
        TextView tvTaskWebLink = convertView.findViewById(R.id.tvTaskWebLink);

        tvTaskTitle.setText(getContext().getString(R.string.item_title) + task.getTitle());
        tvTaskDescription.setText(getContext().getString(R.string.item_description) + task.getDescription());
        tvTaskDate.setText(getContext().getString(R.string.item_date) + task.getDate());
        tvTaskDuration.setText(getContext().getString(R.string.item_duration) + task.getDuration());
        tvTaskContext.setText(getContext().getString(R.string.item_context) + task.getContext());
        tvTaskStatus.setText(getContext().getString(R.string.item_status) + task.getStatus());

        if (!task.getWebLink().isEmpty()) {
            tvTaskWebLink.setText(HtmlCompat.fromHtml("<a href=\"" + task.getWebLink() + "\">Lien web</a>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            tvTaskWebLink.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            tvTaskWebLink.setVisibility(View.GONE);
        }

        return convertView;
    }
}

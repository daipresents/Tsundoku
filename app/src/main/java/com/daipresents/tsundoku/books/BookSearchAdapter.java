package com.daipresents.tsundoku.books;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daipresents.tsundoku.R;

import java.util.List;

/**
 * Created by daipr on 2016/12/07.
 */
public class BookSearchAdapter
        extends ArrayAdapter<BookParcelable> {

    private static final String TAG = BookSearchAdapter.class.getSimpleName();
    private Context context;
    private LayoutInflater layoutInflater;

    static class ViewHolder {
        TextView title;
        TextView author;
        TextView publisher;
        TextView publishedDate;
        TextView description;
        ImageView thumbnail;
    }

    public BookSearchAdapter(Context context, List<BookParcelable> bookList) {
        super(context, 0, bookList);
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.v(TAG, "getView: start");

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = this.layoutInflater.inflate(R.layout.activity_book_search_result_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.searchResultTitle);
            viewHolder.author = (TextView) convertView.findViewById(R.id.searchResultAuthor);
            viewHolder.publisher = (TextView) convertView.findViewById(R.id.searchResultPublisher);
            viewHolder.publishedDate = (TextView) convertView.findViewById(R.id.searchResultpPublishedDate);
            viewHolder.description = (TextView) convertView.findViewById(R.id.searchResultpDescription);
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.searchResultThumbnail);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        BookParcelable item = (BookParcelable) getItem(position);
        viewHolder.title.setText(item.getTitle());
        viewHolder.author.setText(item.getAuthor());
        viewHolder.publisher.setText(item.getPublisher());
        viewHolder.publishedDate.setText(item.getPublishedDate());
        viewHolder.description.setText((item.getDescription()));

        // TODO 仮の画像
        if (item.getThumbnail() != null) {
            Log.v(TAG, "getView: bookImageAsyncTask start.");
            BookImageAsyncTask bookImageAsyncTask = new BookImageAsyncTask(context, viewHolder.thumbnail);
            bookImageAsyncTask.execute(item.getThumbnail());
        }

        return convertView;
    }

}

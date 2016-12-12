package com.daipresents.tsundoku.com.daipresents.tsundoku.booksapi;

import android.content.Context;
import android.content.Loader;
import android.support.v4.content.res.ResourcesCompat;
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
        extends ArrayAdapter<BookItem> {

    private static final String TAG = BookSearchAdapter.class.getSimpleName();
    private Context context;
    private LayoutInflater layoutInflater;

    static class ViewHolder {
        ImageView smallThumnail;
        TextView title;
        TextView author;
    }

    public BookSearchAdapter(Context context, List<BookItem> bookList) {
        super(context, 0, bookList);
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.v(TAG, "getView: start");

        View view = null;
        ViewHolder viewHolder = null;

        if (convertView == null) {
            view = this.layoutInflater.inflate(R.layout.activity_book_search_result_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.smallThumnail = (ImageView) view.findViewById(R.id.smallThumbnail);
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.author = (TextView) view.findViewById(R.id.author);
            view.setTag(viewHolder);

        } else {
            view = convertView;
        }

        BookItem item = (BookItem) getItem(position);

        // Image
        // TODO 仮の画像
        BookImageAsyncTask bookImageAsyncTask = new BookImageAsyncTask(context, viewHolder.smallThumnail);
        bookImageAsyncTask.execute(item.getSmallThumbnail());

        // Title
        viewHolder.title.setText(item.getTitle());

        // Author
        viewHolder.author.setText(item.getAuthor());

        return view;
    }

}

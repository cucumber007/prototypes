package com.cucumber007.prototypes.activities.giver;


public class GalleryRecyclerAdapter  {
/*public class GalleryRecyclerAdapter extends BaseRecyclerViewAdapter<String, GalleryRecyclerAdapter.GalleryImageHolder> {

    public GalleryRecyclerAdapter(Context context, List<String> items, @LayoutRes int itemLayout) {
        super(context, items, itemLayout);
    }


    @Override
    GalleryImageHolder createViewHolder(View view) {
        return new GalleryImageHolder(view);
    }

    @Override
    void bindViewHolder(GalleryImageHolder holder, String item, int position) {
        Glide.with(getContext()).load(item).centerCrop().into(holder.image);
        holder.image.setOnClickListener(view-> {
            if (getItemClickListener() != null) getItemClickListener().onItemClick(position, view, item);
        });
    }

    public class GalleryImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image) ImageView image;

        public GalleryImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }

    public static ArrayList<String> getGalleryImagesPath(Context context) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = context.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);

            listOfAllImages.add(absolutePathOfImage);
        }
        cursor.close();
        return listOfAllImages;
    }*/

}

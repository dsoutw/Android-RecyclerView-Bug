# RecyclerView/ListAdapter bug

## Problems

1. The order of the displayed list is different from the one supplied to ListAdapter.submitList
2. When the display error happens, the method ListAdapter.onBindViewHolder is called repeatedly and never stops. This causes serious preformance and battery life problems.
3. When the display error happens, the ViewHolder UI freezes: touching the item view no longer triggers the onClickListener.

## Descriptions

A minimum example to reproduce the problem is available here:
link

The problem is tested on the emulator of API 34

## Remarks

1. I used ViewPager2 (in fragment_main.xml) to show the fragment (fragment_list.xml) that contains the RecyclerView. It seems that using ViewPager2 is essential to reporduce the problem. The problem will no longer appear if the proxy fragment is removed, i.e. replace

android:name="com.dsou.recyclerview.MainFragment"

by  

android:name="com.dsou.recyclerview.list.MyListFragment"

in activity_main.xml

2. It seems that using the data binding in the holder view is essential. The problem will no longer appear if replace

binding.item = item

by 

binding.itemText.text = item.text

in MyListAdapter.kt, and remove the data binding tags in holder_item.xml

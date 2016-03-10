package com.example.kevin.zhihulightread.model;


import java.util.List;

/**
 * 作者：Created by Kevin on 2016/2/19.
 * 邮箱：haowei0708@163.com
 * 描述：最新新闻的bean
 */
public class LatestNewsBean {
    /**
     * date : 20160302
     * stories : [{"images":["http://pic3.zhimg.com/113389ec06f6f756da43294f049b47a2.jpg"],"type":0,"id":7947546,"ga_prefix":"030217","title":"冰和水都是透明的，那为什么雪是白色的？"},{"title":"围观美国法律界大佬用了一辈子来秀恩爱","ga_prefix":"030216","images":["http://pic4.zhimg.com/fc0007ec389f418d1a3b1099e7aa0067.jpg"],"multipic":true,"type":0,"id":7944470},{"images":["http://pic4.zhimg.com/1b060614ea3e7e53b86346ada37f6da7.jpg"],"type":0,"id":7927376,"ga_prefix":"030215","title":"咳嗽是一种保护，那止咳药是停止这种保护吗？"},{"images":["http://pic1.zhimg.com/8016c66d477f883e7885cc48d7ed5cf4.jpg"],"type":0,"id":7946775,"ga_prefix":"030214","title":"第一次去酒展，如何伪装成一个老手？"},{"images":["http://pic4.zhimg.com/a529718f4ec58bf8e7f53568f05682e7.jpg"],"type":0,"id":7946052,"ga_prefix":"030213","title":"字的上半身都一样，「無」被简化成「无」，但「舞」为什么没变？"},{"images":["http://pic2.zhimg.com/15b93d9edb2aa0b954da4a5b84a162dd.jpg"],"type":0,"id":7945327,"ga_prefix":"030212","title":"这是第一次，上海人口真的在减少"},{"title":"来这儿泡温泉，说不定会遇到打黑工的千寻和无脸男","ga_prefix":"030211","images":["http://pic2.zhimg.com/5b355748b5e56c3d99ce2e93c66f3aa5.jpg"],"multipic":true,"type":0,"id":7931111},{"images":["http://pic1.zhimg.com/ee0f41df8fe10b63a106bf3754d6f12c.jpg"],"type":0,"id":7943697,"ga_prefix":"030210","title":"虽然是晴天，但天就是不蓝"},{"images":["http://pic2.zhimg.com/2d3b4fb45c7db5576bc98bfc12291379.jpg"],"type":0,"id":7918642,"ga_prefix":"030209","title":"有人问草坪上的这只鸟是什么品种，不用看，我几乎可以断定\u2014\u2014"},{"images":["http://pic2.zhimg.com/f238dc4118d05e9f2497c0e373d5a271.jpg"],"type":0,"id":7944486,"ga_prefix":"030208","title":"自己发音不标准，教孩子英语到底行不行？"},{"images":["http://pic4.zhimg.com/51296793e3deaa48510aa55557bb4057.jpg"],"type":0,"id":7916618,"ga_prefix":"030207","title":"每个小火车站都是有用的，只是这些用处我们可能想不到"},{"title":"房产律师告诉你，有关中介费的种种","ga_prefix":"030207","images":["http://pic3.zhimg.com/08a470a34c1993a9c107c4f34c97c802.jpg"],"multipic":true,"type":0,"id":7871465},{"images":["http://pic3.zhimg.com/65e1aa88286fa248cdcd0cc1e9e49cb6.jpg"],"type":0,"id":7941011,"ga_prefix":"030207","title":"为什么瓶装奶茶要用这么多添加剂？"},{"images":["http://pic1.zhimg.com/4df7ba0acacc51d675b421e37f696800.jpg"],"type":0,"id":7944342,"ga_prefix":"030207","title":"读读日报 24 小时热门：有谁欣赏过历届奥斯卡，落选者的表情"},{"images":["http://pic2.zhimg.com/c0feade64f6345688d246d5c441efe55.jpg"],"type":0,"id":7928165,"ga_prefix":"030206","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic3.zhimg.com/c7fdd819ceac94190d647ad18aa79d26.jpg","type":0,"id":7945327,"ga_prefix":"030212","title":"这是第一次，上海人口真的在减少"},{"image":"http://pic3.zhimg.com/83b324903f30f214b53d339aad9bdc36.jpg","type":0,"id":7941582,"ga_prefix":"030121","title":"这不是部「好」电影，但是它挺好看"},{"image":"http://pic4.zhimg.com/be942694ee87bc3cd2a12bc90e02a13b.jpg","type":0,"id":7918642,"ga_prefix":"030209","title":"有人问草坪上的这只鸟是什么品种，不用看，我几乎可以断定\u2014\u2014"},{"image":"http://pic4.zhimg.com/62567e2fdb3ea5941abe7b579f528567.jpg","type":0,"id":7944342,"ga_prefix":"030207","title":"读读日报 24 小时热门：有谁欣赏过历届奥斯卡，落选者的表情"},{"image":"http://pic4.zhimg.com/a596ee85c3e9307e0384b7be742e2477.jpg","type":0,"id":7871465,"ga_prefix":"030207","title":"房产律师告诉你，有关中介费的种种"}]
     */

    private String date;
    /**
     * images : ["http://pic3.zhimg.com/113389ec06f6f756da43294f049b47a2.jpg"]
     * type : 0
     * id : 7947546
     * ga_prefix : 030217
     * title : 冰和水都是透明的，那为什么雪是白色的？
     */

    private List<StoriesEntity> stories;
    /**
     * image : http://pic3.zhimg.com/c7fdd819ceac94190d647ad18aa79d26.jpg
     * type : 0
     * id : 7945327
     * ga_prefix : 030212
     * title : 这是第一次，上海人口真的在减少
     */

    private List<TopStoriesEntity> top_stories;

    public void setDate(String date) {
        this.date = date;
    }

    public void setStories(List<StoriesEntity> stories) {
        this.stories = stories;
    }

    public void setTop_stories(List<TopStoriesEntity> top_stories) {
        this.top_stories = top_stories;
    }

    public String getDate() {
        return date;
    }

    public List<StoriesEntity> getStories() {
        return stories;
    }

    public List<TopStoriesEntity> getTop_stories() {
        return top_stories;
    }

    public static class StoriesEntity {
        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private List<String> images;

        public void setType(int type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public int getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public List<String> getImages() {
            return images;
        }
    }

    public static class TopStoriesEntity {
        private String image;
        private int type;
        private int id;
        private String ga_prefix;
        private String title;

        public void setImage(String image) {
            this.image = image;
        }

        public void setType(int type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public int getType() {
            return type;
        }

        public int getId() {
            return id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public String getTitle() {
            return title;
        }
    }


//    public
//
//    public List<Stories> stories;
//    public List<Top_stories> top_stories;
//
//    public class Stories {
//        public String ga_prefix;//	021922
//        public String id;// 7873017
//        public List<String> images;//	Array
//        public String title;// 标题，深夜惊奇
//        public String type;// 0   ，乎日报可能将某个主题日报的站外文章推送至知乎日报首页时type为1
//    }
//
//
//
//    public class Top_stories{
//       public String  ga_prefix	;//  021920
//       public String  id	    ;//  7894651
//       public String  image	    ;// http://pic4.zhimg.com/f4f827d17bed5677436a6c5c2413ee2f.jpg
//       public String  title	    ;// 这么多叫《XX 挑战》的综艺节目霸屏，到底算不算抄袭？
//       public String  type	    ;//  0
//    }
}

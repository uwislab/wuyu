package com.fiveup.core.remark.service;

import com.fiveup.core.remark.entity.studentScore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;


public class createremarkService {
    public static String createremark(double v_ave, double i_ave, double s_ave, double a_ave, double l_ave, studentScore student) {
        String remark = "";
        String rank = "";
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int e = 0;
        Random rd = new Random();
        String[] v_good ={"思想品德高尚，平时尊敬师长，团结同学，并且拥有十分良好的道德修养以及社会公德，是班级德育的风向标。",
                "拥有杰出的细想品德，聪明正直，德才兼备。经常参加班级活动，拥有着强烈的集体荣誉感，是同学的好榜样。",
                "树立正确的人生观，价值观。并且在校外也不忘遵守社会公德，积极参加班级的各项活动，是老师的好帮手。"};
        String[] v_bad = {"拥有正确的人生观，价值观。与更多的同学建立友谊，积极参加班集体的活动，真正融入班集体。",
                "增强自身的道德修养，从小事做起，无论大小，多为班集体做出自己独一无二的贡献,这样你也能收获更多友谊。。",
                "拥有良好的社会公德，可以从小事做起，比如垃圾入桶，公交为老弱让座，老师相信你的努力会开花结果。"};
        String[] i_good = {"智多星，通过这学期平时的努力表现，以及良好的学习习惯以及学习态度取得了优异的学习成绩，同时也非常重视课外知识学习，恭喜你！",
                "神算子，你杰出的的智育成绩离不开每天的不懈努力，这学期你熟练掌握了自然科学与社会科学基础知识，这会在你未来的成长中帮助你。",
                "小诸葛，你不仅向老师展现了你对基础知识的掌握，同时还训练了自己的基本技能。你能控制自己的学习进程，也令老师刮目相看，加油！"};
        String[] i_bad = {"掌握正确的学习习惯和学习态度，但更高的智育成绩离不开刻苦的努力以及枯燥的训练，如果你能克服困难，成绩一定会突飞猛进。",
                "每天刻苦学习，同时忍受枯燥的做题，但是优异的智育成绩同样离不开高效的学习方法，通过掌握良好的学习习惯，你会发现自己能做到事半功倍。",
                "与班级大多数同学成绩相差不大，但老师相信你的能力绝对可以与最优秀的同学比肩，接下来，老师也会在学习上帮助你，让你拥有端正的学习态度，取得更高成绩"};
        String[] s_good = {"你学习的同时也不忘积极参加体育活动和体育锻炼，遇到问题的时候也有着很强的心理素质，一直保持着优秀的身体素质。",
                "你在校运会的表现令老师和同学都难以忘记，这体现出了你拥有着杰出的身体条件，老师希望你在平时可以带动班级里体育不好的同学一起锻炼，共同进步。",
                "你不仅拥有着健康的体魄，同时也拥有着优秀的心理素质，这二者相辅相成，互相促进，老师希望你能继续保持良好的状态，迎接更好的自己。"};
        String[] s_bad = {"长期坚持适当的体育锻炼是健康人生的保证，锻炼能增强机体的抵抗力，培养人的反应能力和灵敏性，积极参加体育活动也可以提升其他成绩，让你弯道超车。",
                "老师提醒你要多参加体育锻炼，锻炼能使你的体形健美，并且可以锻炼你的意志和毅力，你可以多与班级里的体育健将接触，和他们共同拥有健康的人生。",
                "因为一个人的身体情况固然重要，但是没有良好的心理健康同样不行。你可以将生活安排得规律且充实，这样生活就会变得丰富多彩。"};
        String[] a_good = {"在老师眼里，你是个懂文明讲礼貌的好孩子，经常和同学合作交流，踊跃的参加艺术实践活动，拥有突出的审美能力，祝贺你。",
                "你拥有极强的创新能力，在美术课堂上，你总能令老师眼前一亮。老师希望你能继续提高美育水平，与优秀的同学互相交流，将你的创造力融入进现实生活。",
                "你具有正确理解和善于欣赏现实美和艺术美的知识与能力，形成了对于美和艺术的爱好，老师相信你能更进一步，创造现实美和艺术美的才能和兴趣，按照美的法则建设生活。"};
        String[] a_bad = {"美育是全面发展教育不可缺少的部分，孔子说过“兴于诗，立于礼，成于乐。”老师希望你能更多地参加艺术实践活动，闲暇时也可以去美术馆陶冶情操，提升自己的美育水平。",
                "美是有力量的，蔡元培曾说过“美育是最重要，最基础的人生观教育。”杰出的美育成绩离不开与同学的合作交流，要有一双发现美的眼睛。",
                "美育同样也能体现出一个人的创新能力，乔布斯说过“苹果与其他公司最大的区别就是在追求科技的同时保持对艺术和美的追求。“所以，老师希望你能用端正的态度对待美育，提高自身素，质陶冶情操。"};
        String[] l_good = {"积极主动参加班级劳动，有着优异的劳动表现，在课余时间也能踊跃投身于社会公益活动，起到了良好的带头作用。",
                "带领班级同学一起参加劳动，用自己的劳动热情感染其他同学，非常值得鼓励，同时不忘回报社会，在公益活动中也大放异彩，是班级同学的好榜样。",
                "你拥有高涨的劳动热情，老师你继续坚持下去,劳动锻炼和造就了我们人类。人的伟大其实就在于会劳动、能劳动和爱劳动没有劳动的人生是毫无意义的，能体现劳动的生活是充满幸福的。"};
        String[] l_bad = {"但是只参加班级里的劳动可远远不够，参加社会公益劳动同样重要，每个人都是社会的一份子，大家为社会做贡献的同时，也会受到来自社会的回馈。",
                "所以你要继续提升自己的劳育水平，并且劳育水平的提升也不仅仅局限于学校和社会，在家庭中你也可以多做家务，为父母分忧解难，这会让你感受到劳动的快乐",
                "因为劳动是人类社会存与发展的基本前提，劳动创造了人，劳动创造了社会，劳动创造了文明，只有积极参与劳动，才能拥有充实多彩的人生。"};
        String[] all_good = {"在班级中你表现出色。你的坚强，你的懂事，你的聪慧，老师都看在眼里，喜在心上。你的努力让你取得了优异的成绩。虽然我知道你走向完美的道路上有时会感到疲惫，但只要把握好自己，我想你一定能做一个更出色的人。",
                "你对班级的热心让我感动，对同学的真诚也让大家称道。勤奋好学、锲而不舍的道理也许无须对你多讲，你要记住的是我们耳熟能详的，“不经历风雨怎能见彩虹”这句歌词，朝着自己的目标前进吧，成功就在不远的地方等着你。",
                "你是人缘好，很善良的优秀学生。学习上认真与执著的你给老师留下深刻的印象;劳动中埋头苦干的你令老师很欣赏。如果你能一如既往的走下去，将会是老师、家人、同学的骄傲!要知道，命运的纤绳将永远掌握在自己手中。",
                "踏实与诚实是你成绩突飞猛进的重要保证，你的学习品质和为人处世用为大家称道，你善于利用时间，学习效率较高也得到了同学的肯定。最后老师再送你一句话：欲穷千里目，更上一层楼!",
                "善良的孩子最让人欣赏，恰好你就是;乐观的孩子最若惹人喜爱，恰好你也是;重感情的孩子最值得称赞，恰好还是你。课堂上，你总是专心致志，从你专注的眼神中，老师看到了你的自信，也看到你成绩的进步。",
                "尊师守纪，学习自觉主动的你，成绩优秀。感谢你一直以来辛勤地为班集体做事，减轻了老师的负担，也为你这学期的大幅进步而高兴，相信你是下了一番苦功的。希望你在生活中学会真诚地与人相处，戒骄戒躁，做同学的好榜样，做老师的得力助手。",
                "你是老师眼里的好学生，同学的好朋友。尽管有时也有些许的懈怠。如果你能少懒惰个—些，再倍加努力一些，一定会前途不可限量的。希望你在学习生活中，想更多的办法，带动班级中其他的同学，共同进步。",};
        String[] all_medium = {"“书山有路勤为径，学海无涯苦作舟”。“勤勉”会永远成为你的财富。学习中出现波动是正常，只要我们执着地去作一件事，那么从长远看，胜利必将属于你。请你保持优势科目，提升劣势科目，你将会发现生活就该如此阳光灿烂!",
                "你能严格遵守学校的各项规章制度，尊敬老师，团结同学，但还没有找到很好的学习方法，因而学习成绩未能有更大的进步。请你不要灰心，慢慢来，不懈追求、不断努力。学习上要加强知识的记忆，多练多问，多与成绩优秀的同学交流。我相信你一定会取得更好的成绩。",
                "你是一位聪明机智，尊敬师长，乖巧听话的学生。你有很强的上进心，但是要注意付诸行动行动。这学期，老师看到了你的努力——你在慢慢改变自己。但还不够，你要更努力，用坚强的毅力改变自己的不好的习惯。"};
        String[] all_pass = {"和上学期比你有了很大的进步，或许，前进的路上你已初尝败绩，可喜的是，你已幡然醒悟正在加倍补偿。衷心希望以后的你，能扬鞭奋起勇超他人。你要清楚、进步的唯一方法就是比别人更努力。",
                "你渴望做一个守纪律的学生，但又常常不能约束自己。你渴望为班级争光，但又缺乏勇气与胆量能力还不够。你渴望学习成绩的进步，但又缺乏顽强拼搏的精神。请你不要灰心，慢慢来，只有不懈的追求不断努力，让无数次失败奠定成功的基石，你终能得到成功的喜悦!",
                "你的学习基础是差了一些，但你不要灰心，“世上无难事，只怕有心人”，只要你学习专心些勤奋些细心些，不懂就问。你的成绩会赶上来的。自信些，老师家长同学都在关心你支持你!"};
        if(student.getMoralityScore()>v_ave){
            remark = remark+"品德分数高于班级平均"+String.format("%.2f", student.getMoralityScore()-v_ave)+"分，";
        }
        else if(student.getMoralityScore() == v_ave){
            remark = remark+"品德分数与平均分持平，";
        }
        else{
            remark = remark+"品德分数低于班级平均分"+String.format("%.2f", v_ave-student.getMoralityScore())+"分，";
        }
        if(student.getIntelligenceScore()>i_ave){
            remark = remark+"智力分数高于班级平均"+String.format("%.2f", student.getIntelligenceScore()-i_ave)+"分，";
        }
        else if(student.getIntelligenceScore() == i_ave){
            remark = remark+"智力分数与平均分持平，";
        }
        else{
            remark = remark+"智力分数低于班级平均分"+String.format("%.2f", i_ave-student.getIntelligenceScore())+"分，";
        }
        if(student.getPhysicalScore()>s_ave){
            remark = remark+"体育分数高于班级平均"+String.format("%.2f", student.getPhysicalScore()-s_ave)+"分，";
        }
        else if(student.getPhysicalScore() == s_ave){
            remark = remark+"体育分数与平均分持平，";
        }
        else{
            remark = remark+"体育分数低于班级平均分"+String.format("%.2f", s_ave-student.getPhysicalScore())+"分，";
        }
        if(student.getAestheticScore()>a_ave){
            remark = remark+"美术分数高于班级平均"+String.format("%.2f", student.getAestheticScore()-a_ave)+"分，";
        }
        else if(student.getAestheticScore() == a_ave){
            remark = remark+"美术分数与平均分持平，";
        }
        else{
            remark = remark+"美术分数低于班级平均分"+String.format("%.2f", a_ave-student.getAestheticScore())+"分，";
        }
        if(student.getLabourScore()>l_ave){
            remark = remark+"劳动分数高于班级平均"+String.format("%.2f", student.getLabourScore()-l_ave)+"分。\n";
        }
        else if(student.getLabourScore() == l_ave){
            remark = remark+"劳动分数与平均分持平\n。";
        }
        else{
            remark = remark+"劳动分数低于班级平均分"+String.format("%.2f", l_ave-student.getLabourScore())+"分。\n";
        }
//开始生成教师评语
        if(student.getMoralityScore()>=90){
            int rd1 = rd.nextInt(3);
            remark = remark +"恭喜你获得了如此优秀的德育成绩，在老师眼里你能做到" + v_good[rd1];
            a = 2;
        }
        else if(student.getMoralityScore()>=75){
            remark = remark +"在德育成绩方面，老师看得见你的努力，希望你更进一步，继续加油，向更高水平的同学看齐，同时不忘与其他同学互相提升。";
            a = 1;
        }
        else{
            int rd2 = rd.nextInt(3);
            remark = remark + "在德育成绩方面，老师要提醒你注意喽，老师希望你能做到" + v_bad[rd2];
            a = 0;
        }
        if(student.getIntelligenceScore()>=90){
            int rd3 = rd.nextInt(3);
            remark = remark + "你是班级的" + i_good[rd3];
            b = 2;
        }
        else if(student.getIntelligenceScore()>=75){
            int rd4 = rd.nextInt(3);
            remark = remark + "你能够做到" + i_bad[rd4];
            b = 1;
        }
        else{
            remark = remark + "你的智育方面的努力老师看在眼里，但是你的上升空间非常巨大，老师认为这不是你的真实水平，加油，继续提高。";
            b = 0;
        }
        if(student.getPhysicalScore()>=90){
            int rd5 = rd.nextInt(3);
            remark = remark + "老师对你的体育表现非常满意，" + s_good[rd5];
            c = 2;
        }
        else if(student.getPhysicalScore()>=75){
            remark = remark + "你的体育成绩超过了班里很多同学，但不能骄傲自满，要继续积极参加体育锻炼，同时不忘提升自己的心理素质";
            c = 1;
        }
        else{
            int rd6 = rd.nextInt(3);
            remark = remark + "你的体育成绩不是很理想，" + s_bad[rd6];
            c = 0;
        }
        if(student.getAestheticScore()>=90){
            int rd7 = rd.nextInt(3);
            remark = remark + "通过这学期的学习，老师很高兴你的审美水平取得了如此杰出的成绩，" + a_good[rd7];
            d = 2;
        }
        else if(student.getAestheticScore()>=75){
            remark = remark + "美术绘画是你的强项,但美育水平可不只包括绘画水平，老师希望你增强创新能力，同时与优秀的同学交流心得。";
            d = 1;
        }
        else{
            int rd8 = rd.nextInt(3);
            remark = remark + "你的美育成绩比较落后，但是" + a_bad[rd8];
            d = 0;
        }
        if(student.getLabourScore()>=90){
            int rd9 = rd.nextInt(3);
            remark = remark + "在你身上，老师充分地看到了劳动教育的成果，你能做到" + l_good[rd9];
            e = 2;
        }
        else if(student.getLabourScore()>=75){
            int rd10 = rd.nextInt(3);
            remark = remark + "老师对你的劳育水平期望很高，" + l_bad[rd10];
            e = 1;
        }
        else{
            remark = remark + "在小学教育中，劳育是不可缺少的一环，希望你能积极参加班级和社会中的各项活动，在劳动中创造自己充实的人生。";
            e = 0;
        }
        remark = remark + "\n";
        Process proc;
//        src\main\java\com\fiveup\core\remark\python\decision.py
//        C:\Users\potat\Desktop\pythonProject\decision.py
        try {
            String[] arg = new String[] { "python", "src\\main\\java\\com\\fiveup\\core\\remark\\python\\decision.py", String.valueOf(a), String.valueOf(b), String.valueOf(c), String.valueOf(d), String.valueOf(e)};
            proc = Runtime.getRuntime().exec(arg);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream(),"GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                rank = line;
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException err){
            err.printStackTrace();
        }
        if(rank.equals("2")){
            int rd11 = rd.nextInt(7);
            remark = remark + "你这学期的表现令老师感到非常满意!" + all_good[rd11];
        }
        else if(rank.equals("1")){
            int rd12 = rd.nextInt(3);
            remark = remark + "你这学期的表现令老师比较满意，但是仍然有很大的进步空间。" + all_medium[rd12];
        }
        else if(rank.equals("0")){
            int rd13 = rd.nextInt(3);
            remark = remark + "你这学期的表现差强人意。" + all_pass[rd13];
        }
        System.out.println("success");
        return remark;
    }
}

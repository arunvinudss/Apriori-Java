import java.io.*;
import java.util.*;
import java.sql.*;
class apriori{
/*
The create_subset method()
Return type:void
Input Parameters:
1.Minimum Support Rate
2.Map of unique products in the database
3.List of all the transactions in the database
4.Minimum confidence rate
Job:
According to Apriori principle the subsets of frequents
item sets are also frequent.
This method create subsets of the item sets and sends it
to the check_min_support method to check
the minimum support and stores the frequent item sets.It
runs till the k-frequent item sets becomes one.
Finally it calls generate_association_rule method which
does the rest.
*/
public static void create_subset(int n,Map<String,Integer>
m1,ArrayList<String> data,int min_conf)
{
int n1=0,y=0;
Set<String> a3=new HashSet<String>(m1.keySet());
ArrayList<String> a1=new ArrayList<String>(a3);
ArrayList<ArrayList<String>> a2=new
ArrayList<ArrayList<String>>(); //Stores frequent item
sets
ArrayList<ArrayList<String>> a7=new
ArrayList<ArrayList<String>>(); //Stores subsets of item
sets
ArrayList<String> tempy=new ArrayList<String>();
while(y==0||(a2.get(y).size())>1) //This loop runs till
frequent item sets becomes one
{
if(y==0)
{
ArrayList<String> s1=new ArrayList<String>(m1.keySet());
ArrayList<String> s2=new ArrayList<String>(s1);
ArrayList<String> s11=new ArrayList<String>(s2);
a7.add(new ArrayList<String>(s11));
s2=check_min_support(y,s1,n,data);a2.add(new ArrayList<String>(s2));
s1=new ArrayList<String>();
for(int i=0;i<s2.size();i++)
{
for(int j=i+1;j<s2.size();j++)
{
s1.add(s2.get(i)+" "+s2.get(j));
}
}
a7.add(new ArrayList<String>(s1));
a2.add(new
ArrayList<String>(check_min_support(y+1,s1,n,data)));
}
else
{
ArrayList<String> s1=new ArrayList<String>();
ArrayList<String> ans1=new ArrayList<String>();
ArrayList<String> temp2=new ArrayList<String>();
ArrayList<String> tempy1=new ArrayList<String>();
s1=new ArrayList<String>(a2.get(y));
int b1=0;
while(s1.size()>0)
{
int x1=0;
String m4=s1.get(b1);
String m2[]=m4.split(" ");
StringBuilder sb=new StringBuilder("");
StringTokenizer st=new StringTokenizer("");
int x2=0;
while(x2<y)
{
if(x2!=0)
sb.append(" ");
sb.append(m2[x2]);
x2++;
}
String tosearch=sb.toString();
int x3=x2;
ArrayList<Integer> counts=new ArrayList<Integer>();
while(x1<s1.size())
{
int flag=1;String my_check=s1.get(x1);
st=new StringTokenizer(tosearch);
while(st.hasMoreTokens())
{
if(flag==0)
break;
String m3=st.nextToken();
if(my_check.contains(m3))
flag=1;
else
flag=0;
}
if(flag==1)
{
x2=x3;
String x4[]=my_check.split(" ");
st=new StringTokenizer(my_check);
sb=new StringBuilder("");
while(x2<x4.length)
{
sb.append(x4[x2]);
sb.append(" ");
x2++;
}
tempy1.add(sb.toString());
counts.add(x1);
}
x1++;
}
int hh=0;
for(int yzz=0;yzz<counts.size();yzz++)
{
s1.remove(counts.get(yzz)-hh);
hh++;
}
for(int i=0;i<tempy1.size();i++)
{
for(int j=i+1;j<tempy1.size();j++)
{
ans1.add(tosearch+" "+tempy1.get(i)+" "+tempy1.get(j));
}
}tempy1.clear();
counts.clear();
}
a7.add(new ArrayList<String>(ans1));
a2.add(new
ArrayList<String>(check_min_support(y+1,ans1,n,data)));
}
y++;
}
generate_association_rule(a7,a2,data,min_conf);
}
/*
The check_min_support method()
Return type:ArrayList of frequent item sets that meets the
minimum_support constraint
Input parameters:
1.Minimum Support
2.ArrayList of subsets of item sets
3.Minimum Confidence
4.ArrayList of all the transaction in the database
Job:
This method checks the minimum support of the subsets of
item sets and returns a ArrayList of frequent itemsets
which meets the minimum support constraint.
*/
public static ArrayList<String> check_min_support(int
x,ArrayList<String> s1,int min_conf,ArrayList<String>
data)
{
ArrayList<String> ans=new ArrayList<String>();
if(x==0)
{
int count=0;
StringTokenizer st=new StringTokenizer("");
for(int i=0;i<s1.size();i++)
{
String xy=s1.get(i);
for(int j=0;j<data.size();j++)
{
st=new StringTokenizer(data.get(j));
while(st.hasMoreTokens()){
if(xy.equals(st.nextToken()))
count++;
}
}
if(((count*100/data.size()))>=min_conf)
ans.add(xy);
count=0;
}
}
else
{
for(int i=0;i<s1.size();i++)
{
ArrayList<String> temp1=new ArrayList<String>();
String tocheck=s1.get(i);
int x1=0;
while(x1<data.size())
{
int flag=1;
StringTokenizer st=new StringTokenizer(tocheck);
String m1=data.get(x1);
while(st.hasMoreTokens())
{
if(flag==1)
{
String t1=st.nextToken();
if(m1.contains(t1))
flag=1;
else
flag=0;
}
else
break;
}
if(flag==1)
temp1.add(m1);
x1++;
}
if((temp1.size()*100/data.size())>=min_conf)
ans.add(s1.get(i));
}}
return ans;
}
/*
The check_confidence method
Return type:double array
Input Parameters:
1.The association rule
2.List of all transaction in the database
3.Minimum Confidence Rate
Job:
It's job is to calculate the support of the elements in
the association rule and the support of the elements
in the left hand side of the rule and send it to
generate_association_rule.The values are stored in a
double array.
*/
public static double[] check_confidence(String
x,ArrayList<String> data,int min_confidence)
{
StringTokenizer st=new StringTokenizer(x);
StringBuilder sb=new StringBuilder("");
String check_1="";
String check_2="";
int x1=0;
double c1=0,c2=0;
ArrayList<String> temp1=new ArrayList<String>();
while(st.hasMoreTokens())
{
check_1=st.nextToken();
if(!check_1.equals("--->"))
{
sb.append(check_1);
sb.append(" ");
}
}
check_1=sb.toString().trim();
st=new StringTokenizer(x);
sb=new StringBuilder("");
while(st.hasMoreTokens())
{check_2=st.nextToken();
if(check_2.equals("--->"))
break;
else
{
sb.append(check_2);
sb.append(" ");
}
}
check_2=sb.toString().trim();
while(x1<data.size())
{
int flag=1;
st=new StringTokenizer(check_1);
String m5=data.get(x1);
while(st.hasMoreTokens())
{
if(flag==1)
{
String t1=st.nextToken();
if(m5.contains(t1))
flag=1;
else
flag=0;
}
else
break;
}
if(flag==1)
temp1.add(m5);
x1++;
}
c1=(double) temp1.size()/data.size();
x1=0;
temp1=new ArrayList<String>();
while(x1<data.size())
{
int flag=1;
st=new StringTokenizer(check_2);
String m5=data.get(x1);
while(st.hasMoreTokens()){
if(flag==1)
{
String t1=st.nextToken();
if(m5.contains(t1))
flag=1;
else
flag=0;
}
else
break;
}
if(flag==1)
temp1.add(m5);
x1++;
}
c2=(double) temp1.size()/data.size();
double[] ans=new double[2];
ans[0]=c1*100;
ans[1]=(c1/c2)*100;
return ans;
}
/*
The generate_association_rule method
Return type:void
Input Parameters:
1.All the generated subsets
2.Frequent item sets
3.List of all transactions in the database
4.Minimum confidence
Job:
It's job is to generate association rules of the k-
frequent item sets starting from 2-frequent item sets and
each
generated rule is sent to check_confidence method to
calculate it's confidence and the rules which meets the
confidence
are printed in the console.
*/
public static voidgenerate_association_rule(ArrayList<ArrayList<String>>
all_itemsets,ArrayList<ArrayList<String>>
frequent_itemsets,ArrayList<String> data,int min_conf)
{
int item_count=1,k1=0,k2=0;
System.out.println();
for(ArrayList lo:frequent_itemsets)
{
if(all_itemsets.get(k2).size()!=0)
{
System.out.println();
System.out.println("The total "+item_count+" item datasets
are:");
System.out.println();
for(k1=0;k1<all_itemsets.get(k2).size();k1++)
{
System.out.print("{"+all_itemsets.get(k2).get(k1).toString
());
if(k1+1!=all_itemsets.get(k2).size())
System.out.print("}, ");
else
System.out.print("}");
}
k2++;
System.out.println();
System.out.println();
if(frequent_itemsets.get(k2-1).size()!=0)
{
System.out.println("The frequent "+item_count+" item
datasets are:");
System.out.println();
for(int y=0;y<lo.size();y++)
{
System.out.print("{"+lo.get(y).toString());
if(y+1!=lo.size())
System.out.print("}, ");
else
System.out.print("}");
}
System.out.println();
item_count++;
}}
}
System.out.println();
String strong_rules="";
int level_counter=0;
System.out.println("The strong association rules are :");
System.out.println();
StringBuilder sb=new StringBuilder("");
double xx[]=new double[2];
for(ArrayList x:frequent_itemsets)
{
for(int i=0;i<x.size();i++)
{
int j=0;
String itemset=x.get(i).toString();
StringTokenizer st=new StringTokenizer(itemset);
if(st.countTokens()>1)
{
String[] items=new String[st.countTokens()];
int reverse_checker=items.length;
while(st.hasMoreTokens())
{
items[j]=st.nextToken();
j++;
}
for(int k=0;k<items.length;k++)
{
sb.append(items[k]+" ---> ");
for(int l=0;l<items.length;l++)
{
if(l!=k)
{
sb.append(items[l]+" ");
}
}
strong_rules=sb.toString();
xx=check_confidence(strong_rules,data,min_conf);
if(xx[1]>=min_conf)
System.out.println(strong_rules+" ["+xx[0]+",
"+xx[1]+"]");
sb=new StringBuilder("");
if(reverse_checker>2){
for(int l=0;l<items.length;l++)
{
if(l!=k)
{
sb.append(items[l]+" ");
}
}
sb.append(" ---> "+items[k]);
strong_rules=sb.toString();
xx=check_confidence(strong_rules,data,min_conf);
if(xx[1]>=min_conf)
System.out.println(strong_rules+" ["+xx[0]+",
"+xx[1]+"]");
sb=new StringBuilder("");
}
}}}
}}
/*
The appriori_apply method
Return type:void
Input parameters:
1.Database name
2.Minimum Support
3.Minimum confidence
Job:
It's job is to establish database connectivity and store
the transaction in a hash map and also identify
unique products in the transaction and store it in a
map.Finally it calls create_subset method.
*/
public static void appriori_apply(String db_name,int
min_supp,int min_conf) throws Exception
{
Class.forName("com.mysql.jdbc.Driver");
Connection
conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1/"
+db_name+"","root","");
Statement stmt=conn.createStatement();
String my_query="select tlist from shop_transaction";
ResultSet rs=stmt.executeQuery(my_query);
StringTokenizer st=new StringTokenizer("");String item="";
int value=0;
int num_of_items=0;
Map<String,Integer> m1=new HashMap<String,Integer>();
ArrayList<String> data=new ArrayList<String>();
while(rs.next())
{
String list=rs.getString("tlist");
String te[]=list.split(" ");
Arrays.sort(te);
StringBuilder sb=new StringBuilder("");
for(String x1:te)
{
sb.append(x1);
sb.append(" ");
}
data.add(sb.toString());
st=new StringTokenizer(sb.toString());
while(st.hasMoreTokens())
{
item=st.nextToken();
if(m1.containsKey(item))
{
value=m1.get(item);
value++;
m1.put(item,value);
}
else
{
m1.put(item,value);
}
}
}
Map<String,Integer> m3=new TreeMap<String,Integer>(m1);
rs.close();
stmt.close();
conn.close();
min_supp=min_supp*100/data.size();
create_subset(min_supp,m3,data,min_conf);
}
/*
The main methodIt call appriori_apply in a loop with different database
names and also accepts minimum confidence and support
from the user
*/
public static void main(String args[]) throws Exception
{
int minimum_confidence=0,minimum_support=0;
String
db_name[]={"shop_data","shop_data_1","shop_data_2","shop_d
ata_3","shop_data_4"};
System.out.println("Please enter the minimum support
rate");
BufferedReader br=new BufferedReader(new
InputStreamReader(System.in));
minimum_support=Integer.parseInt(br.readLine());
System.out.println("Please enter the minimum confidence
rate");
minimum_confidence=Integer.parseInt(br.readLine());
for(int i=0;i<db_name.length;i++)
{
System.out.println("---------------------------DATABASE
"+i+" ----------------------------------------");
appriori_apply(db_name[i],minimum_support,minimum_confiden
ce);
}
}
}}

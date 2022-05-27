package tmy;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.util.StringTokenizer;

public class CrimeCount extends Configured implements Tool {
    public static class CrimeCountMap extends Mapper<LongWritable, Text, Text, IntWritable>
    {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
        {
            String line = value.toString();
            Double x = Double.parseDouble(line.split("\t")[0]);
            Double y = Double.parseDouble(line.split("\t")[1]);
            String crime = line.split("\t")[4];
            StringTokenizer tokenizer = new StringTokenizer(crime);
            while(tokenizer.hasMoreTokens())
            {
                word.set(tokenizer.nextToken());
                if (Math.pow(x-1354326.897,2)+Math.pow(y-411447.7828, 2) <= 40000) {
                    context.write(word, one);
                }
            }
        }
    }
    public static class CrimeCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>
    {
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
        {
            int sum = 0;
            for(IntWritable value: values)
            {
                sum += value.get();
            }
            if (key.toString().toUpperCase().contains("ROBBERY") || key.toString().toUpperCase().contains("RAPE")) {
                context.write(key, new IntWritable(sum));
            }

        }

    }

    public int run(String[] args) throws Exception  {

        Job job = new Job(getConf());
        job.setJarByClass(CrimeCount.class);
        job.setJobName("crimecount");

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(CrimeCountMap.class);
        job.setCombinerClass(CrimeCountReducer.class);
        job.setReducerClass(CrimeCountReducer.class);


        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);


        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean success = job.waitForCompletion(true);
        return success ? 0: 1;
    }


    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        int result = ToolRunner.run(new CrimeCount(), args);
        System.exit(result);
    }
}

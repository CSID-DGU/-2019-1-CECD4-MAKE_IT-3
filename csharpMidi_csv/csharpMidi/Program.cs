using System;
using System.IO;

namespace 헤드청크분석
{
    class Program
    {
        static string fname = "ch10.midi";
        static int division = 0;
        static int division_cnt = 0;
        static int count = 0;
        static void Main(string[] args)
        {
            File.WriteAllText("result.csv", String.Empty); // textfile 초기화
            FileStream fs = new FileStream(fname, FileMode.Open);

            while (fs.Position < fs.Length)
            {
                Chunk chunk = Chunk.Parse(fs);

                if (chunk != null)
                {
                    Console.WriteLine("{0} :{1} bytes", chunk.CTString, chunk.Length);
                    using (StreamWriter outputFile = new StreamWriter("result.csv", true))
                    {
                        /*outputFile.Write(chunk.CTString);
                        outputFile.Write(",");
                        outputFile.Write(chunk.Length);
                        outputFile.Write(",");
                        outputFile.WriteLine("");*/

                        outputFile.Close();
                    }
                }

                if(chunk is Header)
                {
                    ViewHeader(chunk as Header);
                }

                if (chunk is Track)
                {
                    ViewTrack(chunk as Track);
                }
            }
        }

        private static void ViewTrack(Track track)
        {
            Console.WriteLine("=== Track Chunk ===");
            int ecnt = 0;
            foreach (MDEvent mdevent in track)
            {
                ecnt++;
                //Console.WriteLine(StaticFunc.HexaString(mdevent.Buffer));
                Console.Write("{0}th delta:{1}", ecnt, mdevent.Delta);

                if (mdevent is MetaEvent)
                {
                    Console.Write("<Meta>");
                    ViewMeta(mdevent as MetaEvent);
                }
                if (mdevent is MidiEvent)
                {
                    Console.Write("<Midi>");
                    ViewMidi(mdevent as MidiEvent);
                }
                if (mdevent is SysEvent)
                {
                    Console.Write("<Sysex>");
                    ViewSysex(mdevent as SysEvent);
                }
                Console.WriteLine(StaticFunc.HexaString(mdevent.Buffer));
            }
        }
        private static void ViewSysex(SysEvent seve)
        {
            Console.WriteLine(seve.Description);
            using (StreamWriter outputFile = new StreamWriter("result.csv", true))
            {
                //outputFile.WriteLine(seve.Description);
            }
        }
        private static void ViewMidi(MidiEvent midievent)
        {
            Console.WriteLine(midievent.Description);
            using (StreamWriter outputFile = new StreamWriter("result.csv", true))
            {
                count += midievent.getDelta();
                division_cnt += midievent.getDelta();
                if(midievent.Description == string.Empty)
                {

                }
                else
                {
                    outputFile.Write(midievent.Description);
                    outputFile.Write(",");
                    outputFile.Write(division_cnt);
                    outputFile.Write(",");
                }
                if (count >= division*4)//여기 수정했음
                {
                    count = 0;
                    outputFile.WriteLine("");
                }
            }
        }
        private static void ViewMeta(MetaEvent metaevent)
        {
            Console.Write("메시지 : {0} ", metaevent.Msg);
            Console.Write("길이 : {0} ", metaevent.Length);
            Console.WriteLine(metaevent.DataString);

            using (StreamWriter outputFile = new StreamWriter("result.csv", true))
            {
                //outputFile.Write("Message");
                //outputFile.Write(",");
                //outputFile.Write(metaevent.Msg);
                //outputFile.Write(",");
                //outputFile.Write("Length");
                //outputFile.Write(",");
                //outputFile.Write(metaevent.Length);
                //outputFile.Write(",");
                //outputFile.Write("Data");
                //outputFile.Write(",");
                //utputFile.WriteLine(metaevent.DataString);
            }
        }

        private static void ViewHeader(Header header)
        {
            Console.WriteLine("=== 헤더 Chunk ===");
            Console.WriteLine(StaticFunc.HexaString(header.Buffer));
            Console.WriteLine("Format : {0}", header.Format);
            Console.WriteLine("Tracks : {0}", header.TrackCount);
            Console.WriteLine("Division : {0}", header.Division);
            Console.WriteLine();
            division = header.Division;
            using (StreamWriter outputFile = new StreamWriter("result.csv", true))
            {
                /*outputFile.Write("Header");
                outputFile.Write(",");
                outputFile.Write("Format");
                outputFile.Write(",");
                outputFile.Write(header.Format);
                outputFile.Write(",");
                outputFile.Write("TrackCount");
                outputFile.Write(",");
                outputFile.Write(header.TrackCount);
                outputFile.Write(",");
                outputFile.Write("Division");
                outputFile.Write(",");
                outputFile.Write(header.Division);
                outputFile.Write("DivisionCnt");
                outputFile.Write(",");
                division_cnt += header.Division;
                outputFile.Write(division_cnt);
                outputFile.Write(",");
                outputFile.WriteLine("");*/

                outputFile.Close();
            }
        }
    }
}

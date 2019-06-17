using System;
using System.IO;

namespace 헤드청크분석
{
    class Program
    {
        static string fname = "tesy.mid";
        static void Main(string[] args)
        {
            FileStream fs = new FileStream(fname, FileMode.Open);

            while (fs.Position < fs.Length)
            {
                Chunk chunk = Chunk.Parse(fs);

                if (chunk != null)
                {
                    Console.WriteLine("{0} :{1} bytes", chunk.CTString, chunk.Length);
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
        }
        private static void ViewMidi(MidiEvent midievent)
        {
            Console.WriteLine(midievent.Description);
        }
        private static void ViewMeta(MetaEvent metaevent)
        {
            Console.Write("메시지 : {0} ", metaevent.Msg);
            Console.Write("길이 : {0} ", metaevent.Length);
            Console.WriteLine(metaevent.DataString);
        }

        private static void ViewHeader(Header header)
        {
            Console.WriteLine("=== 헤더 Chunk ===");
            Console.WriteLine(StaticFunc.HexaString(header.Buffer));
            Console.WriteLine("Format : {0}", header.Format);
            Console.WriteLine("Tracks : {0}", header.TrackCount);
            Console.WriteLine("Division : {0}", header.Division);
            Console.WriteLine();
        }
    }
}

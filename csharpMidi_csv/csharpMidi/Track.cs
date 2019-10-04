using System.Collections;
using System.Collections.Generic;

namespace 헤드청크분석
{
    public class Track:Chunk, IEnumerable
    {
        List<MDEvent> events = new List<MDEvent>();
        public Track(int ctype, int length, byte[] buffer):base(ctype, length, buffer)
        {
            Parsing(buffer);
        }

        public IEnumerator GetEnumerator()
        {
            return events.GetEnumerator();
        }

        private void Parsing(byte[] buffer)
        {
            int offset = 0;
            MDEvent mdevent = null;
            while (offset < buffer.Length)
            {
                mdevent = MDEvent.Parsing(buffer, ref offset, mdevent);
                if (mdevent == null)
                {
                    break;
                }
                events.Add(mdevent);
            }
        }
    }
}

namespace 헤드청크분석
{
    public class MDEvent
    {
        public int Delta
        {
            get;
            private set;
        }
        public byte EventType
        {
            get;
            private set;
        }
        public byte[] Buffer
        {
            get;
            private set;
        }

        public MDEvent(byte evtype, int delta, byte[] buffer)
        {
            EventType = evtype;
            Delta = delta;
            Buffer = buffer;
        }

        public static MDEvent Parsing(byte[] buffer, ref int offset, MDEvent bef_event)
        {
            int oldoffset = offset;
            int delta = StaticFunc.ReadDeltaTime(buffer, ref offset);
            if (buffer[offset] == 0xFF)
            {
                offset++;
                return MetaEvent.MakeEvent(delta, buffer, ref offset, oldoffset);
            }
            if (buffer[offset] < 0xF0)
            {
                return MidiEvent.makeEvent(buffer[offset++], delta, buffer, ref offset, oldoffset, bef_event.EventType);
            }
            return SysEvent.MakeEvent(buffer[offset++], delta, buffer, ref offset, oldoffset);
        }
    }
}

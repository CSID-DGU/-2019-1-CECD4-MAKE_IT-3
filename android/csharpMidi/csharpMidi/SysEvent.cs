using System;

namespace 헤드청크분석
{
    public class SysEvent : MDEvent
    {
        public byte? FData
        {
            get;
            private set;
        }
        public byte[] Data
        {
            get;
            private set;
        }
        public string Description
        {
            get
            {
                switch(EventType)
                {
                    case 0xF0: return "System Exclusive Messages" + StaticFunc.GetString(Data);
                    case 0xF1: return string.Format("MTC Quarter Frame:{0}", FData);
                    case 0xF2: return string.Format("Song position pointer:{0}", StaticFunc.ConvertHostorderS(Buffer, 1));
                    case 0xF3: return string.Format("Song select {0}", FData);
                    case 0xF6: return "Tune request";
                    case 0xF7: return "End of Sysex Message";
                    case 0xF8: return "Timing Clock for Synchronization";
                    case 0xFA: return "Start current sequence";
                    case 0xFB: return "Continue a stopped sequence";
                    case 0xFC: return "Stop a sequence";
                    case 0xFE: return "Active Sensing";
                }
                return "Not supported" + string.Format("{0:X2}", EventType);
            }
        }

        public SysEvent(byte msg, int delta, byte? fdata, byte[] data, byte[] orgbuffer) : base(msg, delta, orgbuffer)
        {
            FData = fdata;
            Data = data;
        }
        public static MDEvent MakeEvent(byte msg, int delta, byte[] buffer, ref int offset, int oldoffset)
        {
            byte? fdata = null;
            byte[] data = null;
            if (msg == 0xF0)
            {
                byte len = buffer[offset++];
                data = new byte[len];
                Array.Copy(buffer, offset, data, 0, len);
                offset += len;
                fdata = len;
            }
            if ((msg == 0xF1) || (msg == 0xF3))
            {
                fdata = buffer[offset++];
            }
            if (msg == 0xF2)
            {
                fdata = buffer[offset++];
                data = new byte[1];
                data[0] = buffer[offset++];
            }
            byte[] buffer2 = new byte[offset - oldoffset];
            Array.Copy(buffer, oldoffset, buffer2, 0, buffer2.Length);
            return new SysEvent(msg, delta, fdata, data, buffer2);
        }
    }
}

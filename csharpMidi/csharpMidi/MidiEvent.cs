using System;

namespace 헤드청크분석
{
    public class MidiEvent:MDEvent
    {
        public byte Fdata
        {
            get;
            private set;
        }
        public byte Sdata
        {
            get;
            private set;
        }
        public string Note
        {
            get
            {
                return StaticFunc.GetNoteName(Fdata);
            }
        }
        public string ControlData
        {
            get
            {
                return StaticFunc.GetInstrumentName(Fdata);
            }
        }
        public string InstrumentName
        {
            get
            {
                return StaticFunc.GetInstrumentName(Fdata);
            }
        }
        public string Status
        {
            get
            {
                if (EventType < 0x80)
                {
                    return "Running Status";
                }
                switch (EventType >> 4)
                {
                    case 0x8: return "Note Off";
                    case 0x9: return "Note On";
                    case 0xA: return "Note after touch";
                    case 0xB: return "Controller";
                    case 0xE: return "Pitch Bend";
                    case 0xC: return "Change Instrument";
                    case 0xD: return "Channel after touch";
                }
                return string.Empty;
            }
        }
        public int Channel
        {
            get
            {
                return EventType & 0x0F;
            }
        }
        public string Description
        {
            get
            {
                switch (EventType >> 4)
                {
                    case 0x8:
                    case 0x9:
                    case 0xA:
                        return MakeNoteVelocity();
                    case 0xB: return MakeControlChange();
                    case 0xE: return MakePitchBend();
                    case 0xC: return MakeInstrument();
                    case 0xD: return MakeChannel();
                    default: return string.Empty;
                }
            }
        }
        private string MakeChannel()
        {
            return string.Format("{0}:{1}:{2}", Status, Delta, Fdata);
        }
        private string MakeInstrument()
        {
            return string.Format("{0}:{1}:{2}", Status, Delta, InstrumentName);
        }
        private string MakePitchBend()
        {
            return string.Format("{0}:{1}:{2}:{3}", Status, Delta, Fdata & 0x7F, Sdata >> 1);
        }
        private string MakeControlChange()
        {
            return string.Format("{0}:{1}:{2}:{3}", Status, Delta, Fdata, ControlData);
        }
        private string MakeNoteVelocity()
        {
            return string.Format("{0}:{1}:음{2}:건반 누르기 속도(세기){3}", Status, Delta, Note, Sdata);
        }
        public MidiEvent(byte etype, int delta, byte fdata, byte sdata, byte[] buffer) : base(etype, delta, buffer)
        {
            Fdata = fdata;
            Sdata = sdata;
        }

        public static MDEvent makeEvent(byte etype, int delta, byte[] buffer, ref int offset, int oldoffset, byte be_evtype)
        {
            byte fdata;
            byte sdata = 0;
            if (etype < 0x80)
            {
                fdata = etype;
                etype = be_evtype;
            }
            else
            {
                fdata = buffer[offset++];
            }

            switch (etype >> 4)
            {
                case 0x8:
                case 0x9:
                case 0xA:
                case 0xB:
                case 0xE:
                    sdata = buffer[offset++];
                    break;
                case 0xC:
                case 0xD:
                    break;
                default: return null;
            }

            byte[] buffer2 = new byte[offset - oldoffset];
            Array.Copy(buffer, oldoffset, buffer2, 0, buffer2.Length);
            return new MidiEvent(etype, delta, fdata, sdata, buffer2);
        }
    }
}

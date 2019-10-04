using System;
using System.IO;
using System.Net;

namespace 헤드청크분석
{
    public class Chunk
    {
        public int CT//청크 유형
        {
            get;
            private set;
        }
        public int Length//청크 길이
        {
            get;
            private set;
        }
        public byte[] Data//데이터
        {
            get;
            private set;
        }
        public string CTString//청크 유형(문자열)
        {
            get
            {
                return StaticFunc.GetString(CT);
            }
        }
        public byte[] Buffer
        {
            get
            {
                byte[] ct_buf = BitConverter.GetBytes(CT);
                int belen = StaticFunc.ConvertHostorder(Length);
                byte[] len_buf = BitConverter.GetBytes(belen);
                byte[] buffer = new byte[ct_buf.Length + len_buf.Length + Data.Length];
                Array.Copy(ct_buf, buffer, ct_buf.Length);
                Array.Copy(len_buf, 0, buffer, ct_buf.Length, len_buf.Length);
                Array.Copy(Data, 0, buffer, ct_buf.Length + len_buf.Length, Data.Length);
                return buffer;
            }
        }

        public static Chunk Parse(Stream stream)
        {
            try
            {
                BinaryReader br = new BinaryReader(stream);
                int ctype = br.ReadInt32();
                int length = br.ReadInt32();
                length = StaticFunc.ConvertHostorder(length);
                byte[] buffer = br.ReadBytes(length);
                int cval = StaticFunc.ConvertHostorder(ctype);
                switch(StaticFunc.ConvertHostorder(ctype))
                {
                    case 0x4d546864: return new Header(ctype, length, buffer);
                    case 0x4d54726b: return new Track(ctype, length, buffer);
                }
                return new Chunk(ctype, length, buffer);
            }
            catch
            {
                return null;
            }
        }

        public Chunk(int ctype, int length, byte[] buffer)
        {
            CT = ctype;
            Length = length;
            Data = buffer;
        }
    }
}
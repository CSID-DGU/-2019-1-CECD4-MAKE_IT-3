namespace 헤드청크분석
{
    public class Header:Chunk
    {
        public int Format//포멧
        {
            get
            {
                return StaticFunc.ConvertHostorderS(Data, 0);
            }
        }

        public int TrackCount//트랙 개수
        {
            get
            {
                return StaticFunc.ConvertHostorderS(Data, 2);
            }
        }

        public int Division//Division
        {
            get
            {
                return StaticFunc.ConvertHostorderS(Data, 4);
            }
        }

        public Header(int ctype, int length, byte[] buffer) : base(ctype, length, buffer)
        {

        }

    }

}
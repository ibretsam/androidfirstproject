# Update 04/11/2022

## Tên App:
Chưa nghĩ ra... (Mọi người cùng suy nghĩ thêm nhé)

## Công nghệ: 
Java + Firebase

## Giao diện:
Mình sẽ có khoảng 6 màn hình chính
 - Đăng ký
 - Đăng nhập
 - Danh sách ChatRoom
 - ChatRoom
 - Danh bạ (danh sách user)
 - Trang cá nhân

## Mô tả tính năng:

 ### Đăng ký:
> Đăng ký cần thông tin SĐT, Mật khẩu, Họ Tên, Hình đại diện (Có thể chưa cần liền, sau này có thời gian sẽ bổ sung sau)
 ### Đăng nhập:
> Đăng nhập bằng sđt, mật khẩu
 ### Danh sách phòng chat:
> Hiển thị danh sách các phòng chat có nội dung tin nhắn. Giao diện từng item bao gồm 1 ảnh đại diện (chọn đại 1 ảnh mặc định), Họ tên, tin nhắn cuối cùng, thời gian.
 ### Phòng chat:
> Hiển thị danh sách các tin nhắn có Id của phòng chat đó, người gửi bên phải, người nhận bên trái. (Giao diện từng item gồm tên người gửi, nội dung, thời gian). Danh sách này lấy dữ liệu từ FireBase, khi có tin nhắn mới phải tự động cập nhật và hiển thị lên.
 ### Gửi tin nhắn:
> Khi nhấn nút gửi, thông tin tin nhắn sẽ được gửi lên FireBase
 ### Danh bạ:
> Hiển thị danh sách, thêm, xóa, sửa người dùng đã được thêm vào danh bạ. Khi thêm người dùng phải kiểm tra sẽ sđt đã có trên firebase chưa, nếu chưa thì không được phép thêm.
 ### Trang cá nhân:
> Sửa thông tin họ tên, ảnh đại diện của người dùng, nút đăng xuất.

## Database Model:
- User:
     + PhoneNumber (Primary Key)
     + FullName
     + PictureLink
- Message:
     + Sender (FK -> User)
     + Receipient (FK -> User)
     + Content
     + ChatRoomID (FK -> ChatRoom)
     + Time
- ChatRoom:
     + Id (Primary Key)
     + User1 (FK -> User)
     + User2 (FK -> User)
     + LastMessage(FK -> Message)
## Dặn dò
Hiện tại nhóm mình chỉ đang chờ Linh làm xong phần giao diện, mọi người nếu có làm những tính năng gì khác thì cứ làm thử ở ngoài, khoan hẵn commit lên git hen, để cho đỡ rối. Đợi Linh làm giao diện xong mình sửa lại rồi tới phần ai làm gì thì mình sẽ chia sau.

Nếu ai thấy có ý tưởng gì hay hoặc muốn thử làm gì cứ nhắn lên nhóm hoặc inbox anh hen. Hiện anh đã làm xong phần đăng ký bằng số điện thoại với firebase rồi. Có gì mọi người kéo về test thử nhé!

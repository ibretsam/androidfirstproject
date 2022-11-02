# Update 02/11/2022

Hi team
Nhóm mình đã chọn làm đề tài App Chat, mình recap nhanh lại nội dung sáng nay nhóm đã họp nhé.

## Tên App:
Chưa nghĩ ra... (Mọi người cùng suy nghĩ thêm nhé)

## Công nghệ: 
Java + Firebase

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

## Giao diện:
Mình sẽ có khoảng 6 màn hình chính
 - Đăng ký
 - Đăng nhập
 - Danh sách ChatRoom
 - ChatRoom
 - Danh bạ (danh sách user)
 - Trang cá nhân

**Database Model:**
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
### Cách làm việc
Mọi người tải git để làm việc chung trên 1 project. Nếu ai chưa biết git là gì thì có thể search google, coi qua những video hướng dẫn trên youtube trước, nếu vẫn chưa hiểu hay gặp vấn đề gì có thể hỏi mình. Link git hiện tại đang chỉ là 1 cái project mới hoàn toàn nên mọi người chưa cần phải clone về. Để sắp tới mình configure thêm cái Firebase rồi sau đó phân công công việc mọi người clone về sau cũng được.

### Phân công công việc:
Hiện tại bữa sau thì thầy chỉ yêu cầu mình thiết kế giao diện, sáng nay Trang đã xung phong phần thiết kế Figma, Linh xung phong phần code giao diện lên App, còn lại cũng chưa có task gì thêm nên các bạn còn lại chắc sẽ khá rảnh. Nếu @Trang khi thiết kế gặp vấn đề gì thì đăng lên cho mọi người biết nha. Mọi người có thể nhận xét, góp ý để cải thiện hơn.
Mới vô nên chắc cũng chưa có nhiều task, mọi người có thể sẽ hơi rảnh, nhưng sắp tới sẽ có rất nhiều việc, mọi người chuẩn bị tinh thần trước cho sắp tới nhé, mình sẽ phân công và check deadline mọi người thường xuyên.

export default function (users, userId) {
    const user = users.find(u => u.user_id === userId);
    return user;
};
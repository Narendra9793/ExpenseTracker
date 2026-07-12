import re

class MessageUtils:
    def isBankSms(self, message):
        words_to_search = ['bank', 'card', 'spent']
        pattern = r'\b(?:' + '|'.join(re.escape(word) for word in words_to_search) + r')\b'
        return bool(re.search(pattern, message, flags=re.IGNORECASE)) 

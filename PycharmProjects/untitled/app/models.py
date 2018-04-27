
from django.db import models
from django.utils import timezone


class Post(models.Model):
    title = models.CharField(max_length=200)
    name = models.CharField(max_length=200)
    year = models.CharField(max_length=200)
    text = models.TextField()


    def __str__(self):
        return self.name
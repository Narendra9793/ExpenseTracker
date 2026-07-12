
from setuptools import setup, find_packages

install_requires = [
    'Flask==3.1.3',
    'kafka-python==3.0.0',
    'langchain-core==1.4.7',
    'langchain-mistralai==1.1.5',
    'pydantic==2.13.4',
    'python-dotenv==1.2.2',
]

setup(
    name='dsservice',
    version='1.0',
    packages=find_packages('src'),
    package_dir={'':'src'},
    install_requires=install_requires,
    include_package_data=True,
)
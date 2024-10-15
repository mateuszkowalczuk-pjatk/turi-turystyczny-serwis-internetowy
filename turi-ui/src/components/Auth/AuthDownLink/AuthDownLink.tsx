import TextRegular from '../../Controls/Text/TextRegular'
import styles from './AuthDownLink.module.css'

interface Props {
    firstLink: string;
    secondLink: string;
}

const AuthDownLink = ({ firstLink, secondLink }: Props) => {
    const isRight = secondLink === "right";
    const isCenter = secondLink === "center";

    return (
        <div className={`${styles.link} ${isCenter ? styles.linkCenter : ''} ${isRight ? styles.linkRight : ''}`}>
            {!isRight && !isCenter ? (
                <>
                    <TextRegular
                        text={firstLink}
                    />
                    <TextRegular
                        text={secondLink}
                    />
                </>
            ) : (
                <TextRegular
                    text={firstLink}
                />
            )}
        </div>
    )
}

export default AuthDownLink;